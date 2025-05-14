package restaurante.example.demo.persistence.dao.implementation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.order.IOrderItemDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.order.OrderItemEntity;
import restaurante.example.demo.persistence.repositoy.order.IOrderItemRepository;

import java.util.List;
import java.util.Optional;
import restaurante.example.demo.persistence.model.order.OrderEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

@Component
public class OrderItemDaoImpl implements IOrderItemDao {

    @Autowired
    private IOrderItemRepository orderItemRepository;

    @Override
    public List<OrderItemEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<OrderItemEntity>) this.orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItemEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.orderItemRepository.findById(id);
    }

    @Override
    public OrderItemEntity save(OrderItemEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.orderItemRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.orderItemRepository.deleteById(id);
    }
        
    @Override
    public void deleteAll(List<OrderItemEntity> items){
          this.orderItemRepository.deleteAll(items);
    }

    @Override
    public List<OrderItemEntity> findByOrder(OrderEntity order) {
       return  this.orderItemRepository.findByOrder(order);
    }

    @Override
    public Optional<OrderItemEntity> findByOrderAndProduct(OrderEntity order, ProductEntity product) {
         return  this.orderItemRepository.findByOrderAndProduct(order, product);
    }
}
