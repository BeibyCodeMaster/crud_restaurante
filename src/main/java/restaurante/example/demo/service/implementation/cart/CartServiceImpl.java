package restaurante.example.demo.service.implementation.cart;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.cart.ICartDao;
import restaurante.example.demo.persistence.dao.interfaces.cart.ICartItemDao;
import restaurante.example.demo.persistence.dao.interfaces.product.IProductDao;
import restaurante.example.demo.persistence.dao.interfaces.user.ICustomerDao;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.persistence.model.cart.CartItemEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.cart.CartDto;
import restaurante.example.demo.presentation.dto.cart.ProductWithQuantityDto;
import restaurante.example.demo.presentation.dto.order.OrderDto;
import restaurante.example.demo.service.interfaces.cart.ICartService;
import restaurante.example.demo.service.interfaces.order.IOrderService;
import restaurante.example.demo.service.mapper.cart.ICartMapper;

@Service
public class CartServiceImpl implements ICartService{

    @Autowired
    private ICartDao cartDao; // DAO para realizar operaciones de base de datos relacionadas con el carrito de compras.

    @Autowired
    private ICartItemDao cartItemDao; // DAO para manejar las operaciones de base de datos de los ítems del carrito.

    @Autowired
    private ICartMapper cartMapper; // Mapeador para transformar entidades de carrito en DTOs y viceversa.

    @Autowired
    private ICustomerDao customerDao; // DAO para realizar operaciones relacionadas con los clientes en la base de datos.

    @Autowired
    private IProductDao productDao; // DAO para gestionar las operaciones relacionadas con los productos en la base de datos.
   
    @Autowired
    private IOrderService orderService; // Servicio para manejar las operaciones relacionadas con pedidos.

        
    
    /**
     * Agrega productos al carrito de compras de un cliente.
     *
     * @param entityDto DTO que contiene los detalles del carrito, incluyendo el cliente y los IDs de los productos.
     * @return Un CartDto actualizado que incluye los productos agregados, el subtotal, el IVA y el total del carrito.
     * @throws EntityDataAccesException Si no se encuentra el cliente, algún producto no existe, 
     *                                  o si ocurre un error en el acceso a los datos.
     */
    @Override
    public CartDto addProductsToCart(CartDto entityDto) throws EntityDataAccesException {

        // Obtiene el cliente asociado al carrito, validando su existencia.
        // Lanza una excepción si el cliente no se encuentra en la base de datos.
        CustomerEntity customer = this.customerDao.findById(entityDto.getCustomer().getId())
                .orElseThrow(() -> new EntityDataAccesException("Cliente no encontrado"));

        // Agrupa los IDs de los productos solicitados junto con la cantidad de cada uno.
        // Esto crea un mapa donde la clave es el ID del producto y el valor es la cantidad solicitada.
        Map<Long, Long> productCounts = entityDto.getItems().stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        // Valida que todos los productos solicitados existan en la base de datos.
        // Si falta algún producto, lanza una excepción.
        List<ProductEntity> products = validateProducts(new ArrayList<>(productCounts.keySet()));

        // Calcula el subtotal del carrito sumando los precios de los productos multiplicados por sus cantidades.
        BigDecimal subtotal = calculateSubtotal(products, productCounts);

        // Calcula el IVA (19%) basado en el subtotal.
        BigDecimal vat = subtotal.multiply(BigDecimal.valueOf(0.19));

        // Calcula el total sumando el subtotal y el IVA.
        BigDecimal total = subtotal.add(vat);

        try {
            // Obtiene el carrito existente del cliente o crea uno nuevo si no existe.
            CartEntity cartEntity = this.createCart(customer);

            // Itera sobre la lista de productos y agrega cada uno al carrito como un ítem.
            // Incluye la cantidad, el precio y el producto asociado.
            for (ProductEntity product : products) {
                long idProduct = product.getId();
                long quantity = productCounts.getOrDefault(idProduct, 0L);
                CartItemEntity cartItemEntity = CartItemEntity.builder()
                        .value(product.getPrice()) // Precio del producto.
                        .amount((byte) quantity)  // Cantidad solicitada.
                        .product(product)         // Producto asociado.
                        .cart(cartEntity)         // Carrito al que pertenece el ítem.
                        .build();
                this.cartItemDao.save(cartItemEntity); // Guarda el ítem en la base de datos.
            }

        // Mapea la entidad del carrito a DTO
        return this.buildCartDto(cartEntity, products, productCounts, vat, total);

        } catch (DataAccessException e) {
            // Maneja cualquier excepción relacionada con la base de datos.
            System.out.println(e.getMessage());
            throw new EntityDataAccesException(e.getMessage());
        }
    }


    /**
     * Recupera el carrito de compras de un cliente basado en su ID.
     * 
     * @param userId ID del cliente.
     * @return DTO del carrito del cliente.
     */
    @Override
    public CartDto getCartByCustomer(Long userId) {
        // Recuperar la entidad del carrito por el ID del cliente
        CartEntity cartEntity = this.cartDao.findByCustomerId(userId)
                .orElseThrow(() -> new EntityDataAccesException("Cliente no encontrado"));

        // Obtener los productos del carrito (como en el formato de addProductsToCart)
        List<CartItemEntity> cartItems = this.cartItemDao.findByCart(cartEntity);
        // obtenemos los productos de acuerdo a su cantidad.
        List<Long> items = cartItems.stream()
                .flatMap(cartItem -> 
                    // Generar un Stream con tantas ocurrencias como indique 'amount'
                    java.util.stream.Stream.generate(() -> cartItem.getProduct().getId())
                            .limit(cartItem.getAmount())
                )
                .toList();
        // Agrupar los productos por su ID y contar cuántas veces aparece cada uno (la cantidad)     
        Map<Long, Long> productCounts = items.stream()
        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        // Obtener los IDs de los productos
        List<Long> productIds = new ArrayList<>(productCounts.keySet());

        // Obtener los productos asociados al carrito por sus IDs
        List<ProductEntity> products = this.productDao.findAllById(productIds);

        // Calcular el subtotal y total
        BigDecimal subtotal = calculateSubtotal(products, productCounts);
        BigDecimal vat = subtotal.multiply(BigDecimal.valueOf(0.19)); // IVA (19%)
        BigDecimal total = subtotal.add(vat);

        // Mapea la entidad del carrito a DTO
        return this.buildCartDto(cartEntity, products, productCounts, vat, total);
    }



    /**
     * Recupera un carrito de compras específico por su ID.
     * 
     * @param id ID del carrito.
     * @return DTO del carrito solicitado.
     */
    @Override
    public CartDto getCartById(Long id) {
        CartEntity cartEntity = this.cartDao.findById(id)
                .orElseThrow(() -> new EntityDataAccesException("Carrito no encontrado"));

        // Obtener los productos del carrito (similar al formato de addProductsToCart)
        List<CartItemEntity> cartItems = this.cartItemDao.findByCart(cartEntity);
        List<Long> productIds = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());

        // Obtiene los productos asociados al carrito
        List<ProductEntity> products = this.productDao.findAllById(productIds);
       
        // obtenemos los productos de acuerdo a su cantidad.
        List<Long> items = cartItems.stream()
                .flatMap(cartItem -> 
                    // Generar un Stream con tantas ocurrencias como indique 'amount'
                    java.util.stream.Stream.generate(() -> cartItem.getProduct().getId())
                            .limit(cartItem.getAmount())
                )
                .toList();
        // Agrupar los productos por su ID y contar cuántas veces aparece cada uno (la cantidad)     
        Map<Long, Long> productCounts = items.stream()
        .collect(Collectors.groupingBy(idItem -> idItem, Collectors.counting()));
        // Calcula el subtotal y total
        BigDecimal subtotal = calculateSubtotal(products, productCounts);
        BigDecimal vat = subtotal.multiply(BigDecimal.valueOf(0.19)); // IVA (19%).
        BigDecimal total = subtotal.add(vat);

        // Mapea la entidad del carrito a DTO
        return this.buildCartDto(cartEntity, products, productCounts, vat, total);

    }


     /**
     * Actualiza un carrito de compras existente.
     * 
     * @param id ID del carrito a actualizar.
     * @param cartDto DTO con los nuevos datos del carrito.
     * @return DTO del carrito actualizado.
     * @throws EntityNotFoundException si el carrito o el cliente no existen.
     */
    @Override
    public CartDto updateCart(Long id, CartDto cartDto) throws EntityNotFoundException {
        // Verifica la existencia del carrito y del cliente asociado.
        CartEntity cartEntity = this.cartDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado: " + id));

        this.customerDao.findById(cartDto.getCustomer().getId())
                .orElseThrow(() -> new EntityDataAccesException("Cliente no encontrado"));

        // Agrupa los IDs de productos y sus cantidades para validar.
        Map<Long, Long> productCounts = cartDto.getItems().stream()
                .collect(Collectors.groupingBy(itemId -> itemId, Collectors.counting()));

        // Verifica la existencia de los productos y elimina ítems antiguos.
        List<ProductEntity> products = validateProducts(new ArrayList<>(productCounts.keySet()));
        List<CartItemEntity> existingItems = this.cartItemDao.findByCart(cartEntity);
        this.cartItemDao.deleteAll(existingItems);

        // Calcula el nuevo subtotal, IVA y total del carrito.
        BigDecimal subtotal = calculateSubtotal(products, productCounts);
        BigDecimal vat = subtotal.multiply(BigDecimal.valueOf(0.19)); // IVA (19%).
        BigDecimal total = subtotal.add(vat);

        try {
            // Agrega los nuevos ítems al carrito.
            for (ProductEntity product : products) {
                long idProduct = product.getId();
                long quantity = productCounts.getOrDefault(idProduct, 0L);
                CartItemEntity cartItemEntity = CartItemEntity.builder()
                        .value(product.getPrice())
                        .amount((byte) quantity)
                        .product(product)
                        .cart(cartEntity)
                        .build();
                this.cartItemDao.save(cartItemEntity);
            }
            
            return this.buildCartDto(cartEntity, products, productCounts, vat, total);

        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new EntityDataAccesException(e.getMessage());
        }
    }

    /**
     * Elimina un carrito de compras por su ID.
     * 
     * @param id ID del carrito a eliminar.
     * @return Mensaje de confirmación.
     * @throws EntityNotFoundException si el carrito no existe.
     */
    @Override
    public String deleteCart(Long id) throws EntityNotFoundException {
        
        CartEntity cartEntity = this.cartDao.findById(id).orElseThrow(() -> new EntityNotFoundException("El carrito con ID " + id + " no existe."));
        List<CartItemEntity> existingItems = this.cartItemDao.findByCart(cartEntity);
        this.cartItemDao.deleteAll(existingItems);
        this.cartDao.delete(id);
        return "El carrito con ID " + id + " fue eliminado.";
    }

     /**
     * Realiza el proceso de checkout de un carrito.
     * 
     * @param cartId ID del carrito a procesar.
     * @return DTO del pedido creado a partir del carrito.
     * @throws EntityNotFoundException si el carrito no existe.
     */
    @Override
    public OrderDto checkoutCart(Long cartId) throws EntityNotFoundException {
        CartEntity cartEntity = this.cartDao.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        CartDto cartDto = this.cartMapper.entityToDto(cartEntity);
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomer(cartDto.getCustomer());
        orderDto.setItems(cartDto.getItems());

        // Crea un pedido utilizando el servicio de pedidos.
        OrderDto createdOrder = this.orderService.create(orderDto);

        // Limpia los ítems del carrito después del checkout.
        List<CartItemEntity> cartItems = this.cartItemDao.findByCart(cartEntity);
        this.cartItemDao.deleteAll(cartItems);
        this.cartDao.delete(cartEntity.getId());

        return createdOrder;
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
        
    @Override
    public CartEntity createCart(CustomerEntity customer) {
        Long id = customer.getId();

        // Creamos o traemos el carrito actual del cliente
        CartEntity cartEntity = this.cartDao.findByCustomerId(id).orElseGet(() -> {
            LocalDateTime date = LocalDateTime.now();
            CartEntity newCart = new CartEntity();
            newCart.setCustomer(customer);
            newCart.setCreatedAt(date);
            return newCart; // Solo devolvemos el nuevo carrito sin persistir aún
        });

        // Guardamos el carrito si es nuevo (no persistido aún)
        if (cartEntity.getId() == null) {
            cartEntity = this.cartDao.save(cartEntity);
        }

        return cartEntity;
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
    
     /**
     * Construye un DTO de pedido basado en la entidad y detalles proporcionados.
     * 
     * @param cartEntity CartEntity carrito de compras.
     * @param products Productos relacionados.
     * @param productCounts Cantidades de cada producto.
     * @param vat IVA calculado.
     * @param total Total calculado.
     * @param status Estado del pedido.
     * @return DTO del carrito.
    */
    private CartDto buildCartDto(CartEntity cartEntity, 
                        List<ProductEntity> products, 
                        Map<Long, Long> productCounts,
                        BigDecimal vat, 
                        BigDecimal total) {
        
                    // Retorna el carrito actualizado.
            CartDto cartDto = this.cartMapper.entityToDto(cartEntity);
            cartDto.setItems(new ArrayList<>(productCounts.keySet()));
            cartDto.setVat(formatToCurrency(vat));
            cartDto.setProducts(products);
            cartDto.setTotal(formatToCurrency(total));
        System.out.println("productCounts " + productCounts);
     
        // Asignar las cantidades por producto en el DTO
        List<ProductWithQuantityDto> productsWithQuantities = productCounts.entrySet().stream()
                .map(entry -> {
                  return  ProductWithQuantityDto.builder()
                    .productId(entry.getKey())
                    .quantity(entry.getValue())
                    .build();                   
                })
                .collect(Collectors.toList());
        cartDto.setProductsWithQuantities(productsWithQuantities);  // Asigna la lista con la cantidad de cada producto
                
        return cartDto;
    }
    
}
