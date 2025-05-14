package restaurante.example.demo.persistence.dao.implementation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.order.IOrderDao;
import restaurante.example.demo.persistence.model.order.OrderEntity;
import restaurante.example.demo.persistence.repositoy.order.IOrderRepository;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDaoImpl implements IOrderDao {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<OrderEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<OrderEntity>) this.orderRepository.findAll();
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.orderRepository.findById(id);
    }

    @Override
    public OrderEntity save(OrderEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.orderRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.orderRepository.deleteById(id);
    }   
 
}
