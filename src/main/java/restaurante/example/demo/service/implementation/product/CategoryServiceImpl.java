package restaurante.example.demo.service.implementation.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.product.ICategoryDao;
import restaurante.example.demo.persistence.model.product.CategoryEntity;
import restaurante.example.demo.presentation.dto.product.CategoryDto;
import restaurante.example.demo.service.interfaces.product.ICategoryService;
import restaurante.example.demo.service.mapper.product.ICategoryMapper;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private ICategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        return this.categoryDao.findAll().stream()
                .map(this.categoryMapper::entityToDto)
                .toList();
    }

    @Override
    public CategoryDto getOneById(Long id) throws EntityNotFoundException {
        return this.categoryDao.findById(id)
                .map(this.categoryMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("La categoria con el id " + id + " no existe."));
    }

    @Override
    public CategoryDto create(CategoryDto entityDto) throws EntityDataAccesException {
        CategoryEntity categoryEntity = this.categoryMapper.dtoToEntity(entityDto);
        try {
            this.categoryDao.save(categoryEntity); // Guarda la entidad en la base de datos
            return this.categoryMapper.entityToDto(categoryEntity); // Devuelve la entidad guardada como DTO
        } catch (DataAccessException e) {
            System.err.println("service/create :" + e.getMessage()); // Log de error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }

    @Override
    public CategoryDto update(Long id, CategoryDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        CategoryEntity categoryEntity =  this.categoryDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La categoria no existe."));
        this.updateEntityFromDto(categoryEntity, entityDto); // Actualiza los campos de la entidad
        try {
            this.categoryDao.save(categoryEntity); // Guarda los cambios en la base de datos
            return this.categoryMapper.entityToDto(categoryEntity); // Devuelve la entidad actualizada como DTO
        } catch (DataAccessException e) {
            System.err.println("service/update :" + e.getMessage()); // Log de error
            throw new EntityDataAccesException("Error interno del servidor."); // Manejo de excepción
        }
    }

    @Override
    public String delete(Long id) throws EntityNotFoundException {
        Optional<CategoryEntity> categoryOptional = this.categoryDao.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new EntityNotFoundException("La categoria con el ID " + id + " no existe.");
        }
        this.categoryDao.delete(id);
        return "La categoria con el ID " + id + " fue eliminada.";
    }

    // Método auxiliar que actualiza los atributos de la entidad Mesa a partir de un DTO
    private void updateEntityFromDto(CategoryEntity categoryEntity, CategoryDto categoryDto){
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.setDescription(categoryDto.getDescription());
        categoryEntity.setImage(categoryDto.getImage());
        categoryEntity.setImagePath(categoryDto.getImagePath());
       // categoryEntity.setUpdatedAt(LocalDateTime.now());
    }
}
