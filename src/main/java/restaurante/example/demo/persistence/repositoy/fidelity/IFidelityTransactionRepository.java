package restaurante.example.demo.persistence.repositoy.fidelity;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.fidelity.FidelityTransactionEntity;

public interface IFidelityTransactionRepository extends CrudRepository<FidelityTransactionEntity,Long> {
}
