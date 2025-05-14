package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restaurante.example.demo.persistence.model.user.EmployeeEntity;

@Repository
public interface IEmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
    
}
