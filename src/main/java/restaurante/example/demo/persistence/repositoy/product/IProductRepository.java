package restaurante.example.demo.persistence.repositoy.product;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.product.ProductEntity;

public interface IProductRepository extends CrudRepository<ProductEntity,Long> {
}
