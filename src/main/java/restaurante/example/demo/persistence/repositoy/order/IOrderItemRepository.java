package restaurante.example.demo.persistence.repositoy.order;


import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.order.OrderEntity;
import restaurante.example.demo.persistence.model.order.OrderItemEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

public interface IOrderItemRepository extends CrudRepository<OrderItemEntity,Long> {  
    List<OrderItemEntity> findByOrder(OrderEntity order);
    Optional<OrderItemEntity> findByOrderAndProduct(OrderEntity order, ProductEntity product);
}
