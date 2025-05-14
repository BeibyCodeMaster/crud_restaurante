package restaurante.example.demo.persistence.dao.implementation.fidely;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.fidelity.IFidelityTransactionDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.fidelity.FidelityTransactionEntity;
import restaurante.example.demo.persistence.repositoy.fidelity.IFidelityTransactionRepository;

import java.util.List;
import java.util.Optional;

@Component
public class FidelyTransactionDaoImpl implements IFidelityTransactionDao {

    @Autowired
    private IFidelityTransactionRepository fidelityTransactionRepository;

    @Override
    public List<FidelityTransactionEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<FidelityTransactionEntity>) this.fidelityTransactionRepository.findAll();
    }

    @Override
    public Optional<FidelityTransactionEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.fidelityTransactionRepository.findById(id);
    }

    @Override
    public FidelityTransactionEntity save(FidelityTransactionEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.fidelityTransactionRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.fidelityTransactionRepository.deleteById(id);
    }
}
