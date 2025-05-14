package restaurante.example.demo.persistence.repositoy.order;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.order.InvoiceEntity;

public interface IInvoiceRepository extends CrudRepository<InvoiceEntity,Long> {
}
