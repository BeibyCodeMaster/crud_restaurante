package restaurante.example.demo.persistence.dao.interfaces.order;

import java.util.List;
import java.util.Optional;
import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.order.OrderEntity;
import restaurante.example.demo.persistence.model.order.OrderItemEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

public interface IOrderItemDao extends ICrudDao<OrderItemEntity> {
    void deleteAll(List<OrderItemEntity> items); 
    List<OrderItemEntity> findByOrder(OrderEntity order);   
    Optional<OrderItemEntity> findByOrderAndProduct(OrderEntity order, ProductEntity product);    
}
