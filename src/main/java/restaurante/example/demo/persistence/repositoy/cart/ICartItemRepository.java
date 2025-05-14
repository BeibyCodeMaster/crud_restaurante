package restaurante.example.demo.persistence.repositoy.cart;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.persistence.model.cart.CartItemEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

public interface ICartItemRepository extends CrudRepository<CartItemEntity,Long> {
    List<CartItemEntity> findByCart(CartEntity order);
    Optional<CartItemEntity> findByCartAndProduct(CartEntity cart, ProductEntity product);
}
