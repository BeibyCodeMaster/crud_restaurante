package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restaurante.example.demo.persistence.model.user.UserEntity;

import java.util.Optional;

@Repository
// Interfaz que extiende de CrudRepository, proporcionando un conjunto de operaciones CRUD básicas para la entidad admin.
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    // Aquí puedes definir sentencias personalizadas para consultas específicas sobre la entidad admin, si es necesario.

    Optional<UserEntity> findByUserName(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.userName = ?1")
    Optional<UserEntity> getUserName(String username);

}
