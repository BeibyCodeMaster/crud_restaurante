package restaurante.example.demo.persistence.repositoy.order;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.order.PaymentMethodEntity;

public interface IPaymentMethodRepository extends CrudRepository<PaymentMethodEntity,Long> {
}
