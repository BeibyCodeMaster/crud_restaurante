package restaurante.example.demo.service.implementation.order;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.order.IOrderDao;
import restaurante.example.demo.persistence.dao.interfaces.order.IOrderItemDao;
import restaurante.example.demo.persistence.dao.interfaces.product.IProductDao;
import restaurante.example.demo.persistence.dao.interfaces.user.ICustomerDao;
import restaurante.example.demo.persistence.enums.OrderStateEnum;
import restaurante.example.demo.persistence.model.order.InvoiceEntity;
import restaurante.example.demo.persistence.model.order.OrderEntity;
import restaurante.example.demo.persistence.model.order.OrderItemEntity;
import restaurante.example.demo.persistence.model.order.OrderStatusEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.cart.ProductWithQuantityDto;
import restaurante.example.demo.presentation.dto.order.OrderDto;
import restaurante.example.demo.service.interfaces.order.IOrderService;
import restaurante.example.demo.service.mapper.order.IOrderMapper;


@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private IOrderDao orderDao; // DAO para manejar operaciones de base de datos relacionadas con pedidos.

    @Autowired
    private IOrderItemDao orderItemDao; // DAO para manejar operaciones de base de datos relacionadas con ítems de pedidos.

    @Autowired
    private IOrderMapper orderMapper; // Mapeador para convertir entre entidades y DTOs de pedidos.

    @Autowired
    private ICustomerDao customerDao; // DAO para manejar operaciones de base de datos relacionadas con clientes.

    @Autowired
    private IProductDao productDao; // DAO para manejar operaciones de base de datos relacionadas con productos.


    /**
     * Obtiene todos los pedidos existentes y los convierte a DTO.
     * 
     * @return Lista de objetos OrderDto.
     */
    @Override
    public List<OrderDto> getAll() {
        // Recupera todos los pedidos, los convierte a DTO y los retorna como lista.
        return this.orderDao.findAll().stream()
                .map(this.orderMapper::entityToDto)
                .toList();
    }

    /**
     * Obtiene un pedido específico por su ID.
     * 
     * @param id ID del pedido.
     * @return OrderDto del pedido encontrado.
     * @throws EntityNotFoundException si el pedido no existe.
     */
    @Override
    public OrderDto getOneById(Long id) throws EntityNotFoundException {
        // Busca el pedido por ID, convierte a DTO o lanza excepción si no existe.
        return this.orderDao.findById(id)
                .map(this.orderMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("El pedido con el id " + id + " no existe."));
    }

    /**
     * Crea un nuevo pedido con detalles como cliente, factura e ítems.
     * 
     * @param entityDto DTO con los datos del pedido.
     * @return DTO del pedido creado.
     * @throws EntityDataAccesException si hay problemas con los datos como cliente o productos no encontrados.
     */
    @Override
    public OrderDto create(OrderDto entityDto) throws EntityDataAccesException {
        // Genera identificadores únicos para el pedido y la factura. 
        String numberOrder = UUID.randomUUID().toString().substring(0, 10);
        String numeroInvoice = UUID.randomUUID().toString().substring(0, 10);
        
        // Valida que el cliente exista, lanzando excepción si no es encontrado.
        CustomerEntity customer = this.customerDao.findById(entityDto.getCustomer().getId())
                .orElseThrow(() -> new EntityDataAccesException("Cliente no encontrado"));
        
        // Agrupa los IDs de los productos y cuenta las cantidades.
        Map<Long, Long> productCounts = entityDto.getItems().stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        
        // Valida la existencia de los productos solicitados.
        List<ProductEntity> products = validateProducts(new ArrayList<>(productCounts.keySet()));
        
        // Calcula los valores de la factura: subtotal, IVA y total.
        BigDecimal subtotal = calculateSubtotal(products, productCounts);
        BigDecimal vat = subtotal.multiply(BigDecimal.valueOf(0.19)); // 19% de IVA.
        BigDecimal total = subtotal.add(vat);
        
        // Crea una nueva entidad de factura con los valores calculados.
        InvoiceEntity invoice = InvoiceEntity.builder()
                .number(numeroInvoice)
                .date(LocalDate.now())
                .subtotal(subtotal)
                .vat(vat)
                .total(total)
                .build();
        
        // Crea el estado inicial del pedido.
        OrderStateEnum status = OrderStateEnum.valueOf(entityDto.getStatus());
        OrderStatusEntity initialState = OrderStatusEntity.builder()
                .state(status)
                .description("Pedido tomado.")
                .build();
        
        // Construye la entidad del pedido.
        OrderEntity orderEntity = OrderEntity.builder()
                .number(numberOrder)
                .date(LocalDate.now())
                .customer(customer)
                .orderStatus(initialState)
                .invoice(invoice)
                .build();

        try {
            // Guarda el pedido en la base de datos.
            OrderEntity savedOrder = this.orderDao.save(orderEntity);
            
            // Crea los ítems del pedido basados en los productos y cantidades.
            for (ProductEntity product : products) {
                long idProduct = product.getId();
                long quantity = productCounts.getOrDefault(idProduct, 0L);
                OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                        .value(product.getPrice())
                        .amount((byte) quantity)
                        .product(product)
                        .order(savedOrder)
                        .build();
                this.orderItemDao.save(orderItemEntity);
            }
            // Convierte y retorna el pedido guardado como DTO.
            return buildOrderDto(savedOrder, products, productCounts, subtotal, vat, total, entityDto.getStatus());
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new EntityDataAccesException(e.getMessage());
        }    
    }


    /**
     * Actualiza un pedido existente con nuevos datos.
     * 
     * @param id ID del pedido a actualizar.
     * @param entityDto DTO con los nuevos datos del pedido.
     * @return DTO del pedido actualizado.
     * @throws EntityNotFoundException si el pedido no existe.
     * @throws EntityDataAccesException si uno o más productos no existen.
     */
    @Override
    public OrderDto update(Long id, OrderDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        // Validar que el pedido existe.
        OrderEntity orderEntity = this.orderDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado: " + id));

        // Agrupar los IDs de los productos y contar las cantidades.
        Map<Long, Long> productCounts = entityDto.getItems().stream()
                .collect(Collectors.groupingBy(itemId -> itemId, Collectors.counting()));

        // Validar que los productos existen.
        List<ProductEntity> products = validateProducts(new ArrayList<>(productCounts.keySet()));

        // Eliminar los artículos existentes del pedido.
        List<OrderItemEntity> existingItems = this.orderItemDao.findByOrder(orderEntity);
        this.orderItemDao.deleteAll(existingItems);

        // Recalcular los valores de la factura.
        BigDecimal subtotal = calculateSubtotal(products, productCounts);
        BigDecimal vat = subtotal.multiply(BigDecimal.valueOf(0.19));
        BigDecimal total = subtotal.add(vat);

        // Actualizar la factura existente.
        InvoiceEntity invoice = orderEntity.getInvoice();
        invoice.setSubtotal(subtotal);
        invoice.setVat(vat);
        invoice.setTotal(total);
        orderEntity.setInvoice(invoice);

        // Procesar los artículos del pedido.
        for (ProductEntity product : products) {
            long idProduct = product.getId();
            long quantity = productCounts.getOrDefault(idProduct, 0L);
            OrderItemEntity newItem = OrderItemEntity.builder()
                    .value(product.getPrice())
                    .amount((byte) quantity)
                    .product(product)
                    .order(orderEntity)
                    .build();
            this.orderItemDao.save(newItem);
        }
        
        // Guardar los cambios en la base de datos.
        this.orderDao.save(orderEntity);

        // Convertir y retornar el DTO.
        return buildOrderDto(orderEntity, products, productCounts, subtotal, vat, total, entityDto.getStatus());
    }

    /**
     * Elimina un pedido por su ID.
     * 
     * @param id ID del pedido a eliminar.
     * @return Mensaje confirmando la eliminación.
     * @throws EntityNotFoundException si el pedido no existe.
     */
    @Override
    public String delete(Long id) throws EntityNotFoundException {
        Optional<OrderEntity> orderOptional = this.orderDao.findById(id);
        if (orderOptional.isEmpty()) {
            throw new EntityNotFoundException("El pedido con el ID " + id + " no existe.");
        }
        this.orderDao.delete(id);
        return "El pedido con el ID " + id + " fue eliminado.";
    }

    /**
     * Valida que una lista de IDs de productos existe en la base de datos.
     * 
     * @param productIds Lista de IDs de productos.
     * @return Lista de entidades de productos.
     * @throws EntityDataAccesException si uno o más productos no existen.
     */
    private List<ProductEntity> validateProducts(List<Long> productIds) throws EntityDataAccesException {
        List<ProductEntity> products = this.productDao.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new EntityDataAccesException("Uno o más productos no fueron encontrados");
        }
        return products;
    }


    /**
     * Calcula el subtotal de los productos de un pedido.
     * 
     * @param products Lista de productos.
     * @param productCounts Map con cantidades de cada producto.
     * @return Subtotal como BigDecimal.
     */
    private BigDecimal calculateSubtotal(List<ProductEntity> products, Map<Long, Long> productCounts) {
        return products.stream()
                .map(product -> {
                    long id = product.getId();
                    long quantity = productCounts.getOrDefault(id,0l); // Valor predeterminado 0 si no existe
                    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
  
    
    /**
     * Construye un DTO de pedido basado en la entidad y detalles proporcionados.
     * 
     * @param orderEntity Entidad del pedido.
     * @param products Productos relacionados.
     * @param productCounts Cantidades de cada producto.
     * @param subtotal Subtotal calculado.
     * @param vat IVA calculado.
     * @param total Total calculado.
     * @param status Estado del pedido.
     * @return DTO del pedido.
    */
    private OrderDto buildOrderDto(OrderEntity order, 
                        List<ProductEntity> products, 
                        Map<Long, Long> productCounts,
                        BigDecimal subtotal,
                        BigDecimal vat, 
                        BigDecimal total, 
                        String status) {
        OrderDto orderDto = this.orderMapper.entityToDto(order);
        orderDto.setStatus(status);
        orderDto.setItems(new ArrayList<>(productCounts.keySet()));
        orderDto.setProducts(products);
        orderDto.setTotal(formatToCurrency(total));
        orderDto.getInvoice().setSubtotal(formatToCurrency(subtotal));
        orderDto.getInvoice().setTotal(formatToCurrency(total));
        orderDto.getInvoice().setVat(formatToCurrency(vat));

        // Asignar las cantidades por producto en el DTO
        List<ProductWithQuantityDto> productsWithQuantities = productCounts.entrySet().stream()
                .map(entry -> {
                  return  ProductWithQuantityDto.builder()
                    .productId(entry.getKey())
                    .quantity(entry.getValue())
                    .build();                   
                })
                .collect(Collectors.toList());
        orderDto.setProductsWithQuantities(productsWithQuantities);  // Asigna la lista con la cantidad de cada producto
        
        
        return orderDto;
    }
    
    /**
     * formatear el precio a pesos colombianos.
     * 
     * @param value BigDecimal valor del pedido.
     * @return String con el valor del pedido formateado.
    */
    private String formatToCurrency(BigDecimal value) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return currencyFormatter.format(value);
    }
    
}
