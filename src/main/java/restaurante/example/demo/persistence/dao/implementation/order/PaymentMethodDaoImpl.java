package restaurante.example.demo.persistence.dao.implementation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.order.IPaymentMethodDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.order.PaymentMethodEntity;
import restaurante.example.demo.persistence.repositoy.order.IPaymentMethodRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PaymentMethodDaoImpl implements IPaymentMethodDao {

    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<PaymentMethodEntity>) this.paymentMethodRepository.findAll();
    }

    @Override
    public Optional<PaymentMethodEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.paymentMethodRepository.findById(id);
    }

    @Override
    public PaymentMethodEntity save(PaymentMethodEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.paymentMethodRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.paymentMethodRepository.deleteById(id);
    }
}
