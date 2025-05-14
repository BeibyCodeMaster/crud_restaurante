package restaurante.example.demo.persistence.repositoy.order;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.order.OrderEntity;

public interface IOrderRepository extends CrudRepository<OrderEntity,Long> {
}
