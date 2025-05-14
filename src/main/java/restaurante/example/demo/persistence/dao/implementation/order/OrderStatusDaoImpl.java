package restaurante.example.demo.persistence.dao.implementation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.order.IOrderStatusDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.order.OrderStatusEntity;
import restaurante.example.demo.persistence.repositoy.order.IOrderStatusRepository;

import java.util.List;
import java.util.Optional;

@Component
public class OrderStatusDaoImpl implements IOrderStatusDao {

   @Autowired
   private IOrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatusEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<OrderStatusEntity>) this.orderStatusRepository.findAll();
    }

    @Override
    public Optional<OrderStatusEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.orderStatusRepository.findById(id);
    }

    @Override
    public OrderStatusEntity save(OrderStatusEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.orderStatusRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.orderStatusRepository.deleteById(id);
    }
}
