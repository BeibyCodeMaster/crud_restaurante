package restaurante.example.demo.persistence.repositoy.product;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.product.MenuEntity;

public interface IMenuRepository extends CrudRepository<MenuEntity,Long> {
}
