package restaurante.example.demo.persistence.dao.implementation.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.product.ICategoryDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.product.CategoryEntity;
import restaurante.example.demo.persistence.repositoy.product.ICategoryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDaoImpl implements ICategoryDao {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<CategoryEntity>) this.categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.categoryRepository.findById(id);
    }

    @Override
    public CategoryEntity save(CategoryEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.categoryRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.categoryRepository.deleteById(id);
    }
}
