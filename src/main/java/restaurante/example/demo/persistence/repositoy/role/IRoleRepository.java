package restaurante.example.demo.persistence.repositoy.role;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.role.RoleEntity;

import java.util.List;

public interface IRoleRepository extends CrudRepository<RoleEntity, Long> {

}
