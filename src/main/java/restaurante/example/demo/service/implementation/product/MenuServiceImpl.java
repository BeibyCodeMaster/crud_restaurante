package restaurante.example.demo.service.implementation.product;

import java.math.BigDecimal;
import java.text.NumberFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.product.IMenuDao;
import restaurante.example.demo.persistence.model.product.MenuEntity;
import restaurante.example.demo.presentation.dto.product.MenuDto;
import restaurante.example.demo.service.interfaces.product.IMenuService;
import restaurante.example.demo.service.mapper.product.IMenuMapper;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import restaurante.example.demo.persistence.dao.interfaces.product.IProductDao;
import restaurante.example.demo.persistence.model.product.ProductEntity;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IMenuMapper menuMapper;
    
    @Autowired
    private IProductDao productDao;

    @Override
    public List<MenuDto> getAll() {
        return this.menuDao.findAll().stream()
                .map(menu -> {
                    MenuDto menuDto = this.menuMapper.entityToDto(menu);
                    // Calcular el totalPrice del menú
                    BigDecimal totalPrice = menu.getProducts().stream()
                        .map(product -> product.getPrice()) // Acceder al precio de cada producto
                        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar los precios
                    
                    // formatear el precio a pesos colombianos
                     NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es","CO"));
                     String formattedTotalPrice = currencyFormatter.format(totalPrice);       

                    
                    
                    menuDto.setTotalPrice(formattedTotalPrice);
                    return menuDto;
                })
                .toList();
    }

    @Override
    public MenuDto getOneById(Long id) throws EntityNotFoundException {
        MenuEntity menuEntity = this.menuDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El menu con el id " + id + " no existe."));
        
        MenuDto menuDto = this.menuMapper.entityToDto(menuEntity);
        // Calcular el totalPrice del menú
        BigDecimal totalPrice = menuEntity.getProducts().stream()
            .map(product -> product.getPrice()) // Acceder al precio de cada producto
            .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar los precios
        
            // formatear el precio a pesos colombianos
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es","CO"));
            String formattedTotalPrice = currencyFormatter.format(totalPrice);       
        
        menuDto.setTotalPrice(formattedTotalPrice);

        return menuDto;      
    }

    
    @Override
    public MenuDto create(MenuDto entityDto) throws EntityDataAccesException {
        System.out.println("Menu dto : " + entityDto);
        MenuEntity menuEntity = this.menuMapper.dtoToEntity(entityDto);

        // Verificar y obtener las entidades gestionadas de productos
        Set<ProductEntity> managedProducts = entityDto.getProducts().stream()
            .map(productDto -> productDao.findById(productDto.getId())
                .orElseThrow(() -> new EntityDataAccesException(
                    "El producto con id " + productDto.getId() + " no existe."
                )))
            .collect(Collectors.toSet());

        menuEntity.setProducts(managedProducts); // Asociar los productos gestionados

        try {
            this.menuDao.save(menuEntity); // Guardar el menú
            // Calcular el precio total del menú
            BigDecimal totalPrice = menuEntity.getProducts().stream()
                .map(ProductEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);      
                        
            // formatear el precio a pesos colombianos
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es","CO"));
            String formattedTotalPrice = currencyFormatter.format(totalPrice);
            
            MenuDto menuDto =  this.menuMapper.entityToDto(menuEntity); // Devolver el DTO guardado            
            menuDto.setTotalPrice(formattedTotalPrice); // Actualizar el DTO
            
            return menuDto;
            
            
        } catch (DataAccessException e) {
            System.err.println("service/create :" + e.getMessage()); // Log del error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }

    
    
    /*
    @Override
    public MenuDto create(MenuDto entityDto) throws EntityDataAccesException {
        System.out.println("Menu dto : "  + entityDto);
        MenuEntity menuEntity = this.menuMapper.dtoToEntity(entityDto);
        try {
            this.menuDao.save(menuEntity); // Guarda la entidad en la base de datos
            // Calcular el totalPrice del menú
            BigDecimal totalPrice = menuEntity.getProducts().stream()
                .map(product -> product.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            entityDto.setTotalPrice(totalPrice); // Establecer el precio total en el DTO

            return this.menuMapper.entityToDto(menuEntity); // Devuelve la entidad guardada como DTO
        } catch (DataAccessException e) {
            System.err.println("service/create :" + e.getMessage()); // Log de error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }
*/
    @Override
    public MenuDto update(Long id, MenuDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        MenuEntity menuEntity =  this.menuDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El menu no existe."));
        this.updateEntityFromDto(menuEntity, entityDto); // Actualiza los campos de la entidad

        try {
            this.menuDao.save(menuEntity); // Guarda los cambios en la base de datos
            // Calcular el totalPrice del menú
            BigDecimal totalPrice = menuEntity.getProducts().stream()
                .map(product -> product.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
            // formatear el precio a pesos colombianos
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es","CO"));
            String formattedTotalPrice = currencyFormatter.format(totalPrice);            
            
            entityDto.setTotalPrice(formattedTotalPrice); // Establecer el precio total en el DTO

            return this.menuMapper.entityToDto(menuEntity); // Devuelve la entidad actualizada como DTO
        } catch (DataAccessException e) {
            System.err.println("service/update :" + e.getMessage()); // Log de error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }

    @Override
    public String delete(Long id) throws EntityNotFoundException {
        Optional<MenuEntity> menuOptional = this.menuDao.findById(id);
        if (menuOptional.isEmpty()) {
            throw new EntityNotFoundException("El menu con el ID " + id + " no existe.");
        }
        this.menuDao.delete(id);
        return "El menu con el ID " + id + " fue eliminada.";
    }
    
     // Método auxiliar que actualiza los atributos de la entidad Menu a partir de un DTO
    private void updateEntityFromDto(MenuEntity menuEntity, MenuDto menuDto){
        menuEntity.setName(menuDto.getName());
        menuEntity.setDescription(menuDto.getDescription());
        menuEntity.setImage(menuDto.getImage());
        menuEntity.setImagePath(menuDto.getImagePath());
        // menuEntity.setUpdatedAt(LocalDateTime.now());
    }
   
}
