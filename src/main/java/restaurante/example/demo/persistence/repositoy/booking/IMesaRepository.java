package restaurante.example.demo.persistence.repositoy.booking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restaurante.example.demo.persistence.model.booking.MesaEntity;

@Repository
// Interfaz que extiende de CrudRepository, proporcionando un conjunto de operaciones CRUD básicas para la entidad Mesa.
public interface IMesaRepository extends CrudRepository<MesaEntity, Long> {
    // Aquí puedes definir sentencias personalizadas para consultas específicas sobre la entidad Mesa, si es necesario.
}
