package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restaurante.example.demo.persistence.model.user.AdministratorEntity;

@Repository
// Interfaz que extiende de CrudRepository, proporcionando un conjunto de operaciones CRUD básicas para la entidad admin.
public interface IAdministratorRepository extends CrudRepository<AdministratorEntity, Long> {
    // Aquí puedes definir sentencias personalizadas para consultas específicas sobre la entidad admin, si es necesario.
}
