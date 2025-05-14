package restaurante.example.demo.service.implementation.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.product.IProductDao;
import restaurante.example.demo.presentation.dto.product.CategoryDto;
import restaurante.example.demo.presentation.dto.product.ProductDto;
import restaurante.example.demo.service.interfaces.product.IProductService;
import restaurante.example.demo.service.mapper.product.IProductMapper;

import java.util.List;
import java.util.Optional;
import restaurante.example.demo.persistence.dao.interfaces.product.ICategoryDao;
import restaurante.example.demo.persistence.model.product.CategoryEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IProductMapper productMapper;

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public List<ProductDto> getAll() {
        return this.productDao.findAll().stream()
                .map(this.productMapper::entityToDto)
                .toList();
    }

    @Override
    public ProductDto getOneById(Long id) throws EntityNotFoundException {
        return this.productDao.findById(id)
                .map(this.productMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("El producto con el id " + id + " no existe."));
    }

    @Override
    public ProductDto create(ProductDto entityDto) throws EntityDataAccesException {
        Long categoryId = entityDto.getCategory().getId();
        CategoryEntity categoryEntity = this.categoryDao.findById(categoryId)
                .orElseThrow(() -> new EntityDataAccesException("La categoria con el id " + categoryId + " no existe."));

        ProductEntity productEntity = this.productMapper.dtoToEntity(entityDto);
        productEntity.setCategory(categoryEntity); // Establece la categoría relacionada

        try {
            this.productDao.save(productEntity); // Guarda el producto con la categoría asignada
            return this.productMapper.entityToDto(productEntity); // Devuelve el DTO del producto guardado
        } catch (EntityDataAccesException e) {
            System.err.println("service/create :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    public ProductDto update(Long id, ProductDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        
       ProductEntity productEntity = this.productDao.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("El producto con el ID " + id + " no existe."));

       // Actualizamos la entidad con los valores del DTO
       productEntity.setName(entityDto.getName());
       productEntity.setDescription(entityDto.getDescription());
       productEntity.setImage(entityDto.getImage());
       productEntity.setImagePath(entityDto.getImagePath());
       productEntity.setPrice(entityDto.getPrice());
       productEntity.setStatus(entityDto.getStatus());

       // Validar si la categoría cambió
       Long newCategoryId = entityDto.getCategory().getId();
       if (!productEntity.getCategory().getId().equals(newCategoryId)) {
           CategoryEntity newCategory = this.categoryDao.findById(newCategoryId)
                   .orElseThrow(() -> new EntityNotFoundException("La categoría con el ID " + newCategoryId + " no existe."));
           productEntity.setCategory(newCategory);
       }

       try {
           this.productDao.save(productEntity); // Guardar los cambios
           return this.productMapper.entityToDto(productEntity); // Retornar el DTO actualizado
       } catch (EntityDataAccesException e) {
           System.err.println("service/update :" + e.getMessage());
           throw new EntityDataAccesException("Error interno del servidor.");
       } 
    
    
    }

    @Override
    public String delete(Long id) throws EntityNotFoundException {
         Optional<ProductEntity> productOptional = this.productDao.findById(id);
        if (productOptional.isEmpty()) {
            throw new EntityNotFoundException("El producto con el ID " + id + " no existe.");
        }
        this.productDao.delete(id);
        return "El producto con el ID " + id + " fue eliminado.";
    }
        
    private void updateEntityFromDto(ProductEntity productEntity, ProductDto productDto) {
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setImage(productDto.getImage());
        productEntity.setImagePath(productDto.getImagePath());
        CategoryDto categoryDto = productDto.getCategory();
        CategoryEntity categoryEntity = this.categoryDao.findById(categoryDto.getId()).orElseThrow();
        productEntity.setCategory(categoryEntity); // Actualiza la categoría
    }
        
}
