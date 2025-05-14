package restaurante.example.demo.persistence.repositoy.cart;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.cart.CartEntity;

public interface ICartRepository extends CrudRepository<CartEntity,Long> {
    Optional<CartEntity> findByCustomerId(Long customerId);
}
