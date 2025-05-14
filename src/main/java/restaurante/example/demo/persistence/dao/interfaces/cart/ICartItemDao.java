package restaurante.example.demo.persistence.dao.interfaces.cart;

import java.util.List;
import java.util.Optional;
import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.persistence.model.cart.CartItemEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

public interface ICartItemDao extends ICrudDao<CartItemEntity> {
    void deleteAll(List<CartItemEntity> items); 
    List<CartItemEntity> findByCart(CartEntity cartEntity);
    Optional<CartItemEntity> findByCartAndProduct(CartEntity cartEntity, ProductEntity productEntity);  
}
