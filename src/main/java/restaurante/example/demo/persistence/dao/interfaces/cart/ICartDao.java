package restaurante.example.demo.persistence.dao.interfaces.cart;

import java.util.Optional;
import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.cart.CartEntity;

public interface ICartDao extends ICrudDao<CartEntity> {
    
    Optional<CartEntity> findByCustomerId(Long id);
    
    
}
