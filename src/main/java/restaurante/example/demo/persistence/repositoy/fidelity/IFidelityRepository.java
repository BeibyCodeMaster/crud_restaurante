package restaurante.example.demo.persistence.repositoy.fidelity;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.fidelity.FidelityEntity;

public interface IFidelityRepository extends CrudRepository<FidelityEntity,Long> {
}
