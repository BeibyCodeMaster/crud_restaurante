package restaurante.example.demo.persistence.repositoy.product;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.product.CategoryEntity;

public interface ICategoryRepository extends CrudRepository<CategoryEntity,Long> {
}
