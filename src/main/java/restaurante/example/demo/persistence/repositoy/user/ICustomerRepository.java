package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restaurante.example.demo.persistence.model.user.CustomerEntity;

@Repository
public interface ICustomerRepository extends CrudRepository<CustomerEntity, Long> {
    
}
