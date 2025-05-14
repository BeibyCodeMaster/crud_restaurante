package restaurante.example.demo.persistence.dao.implementation.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.order.IInvoiceDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.order.InvoiceEntity;
import restaurante.example.demo.persistence.repositoy.order.IInvoiceRepository;

import java.util.List;
import java.util.Optional;

@Component
public class InvoiceDaoImpl implements IInvoiceDao {

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public List<InvoiceEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<InvoiceEntity>) this.invoiceRepository.findAll();
    }

    @Override
    public Optional<InvoiceEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.invoiceRepository.findById(id);
    }

    @Override
    public InvoiceEntity save(InvoiceEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.invoiceRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.invoiceRepository.deleteById(id);
    }
}
