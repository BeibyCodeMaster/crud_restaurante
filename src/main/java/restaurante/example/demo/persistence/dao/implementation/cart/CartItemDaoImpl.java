package restaurante.example.demo.persistence.dao.implementation.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.cart.ICartItemDao;
import restaurante.example.demo.persistence.model.cart.CartItemEntity;
import restaurante.example.demo.persistence.repositoy.cart.ICartItemRepository;

import java.util.List;
import java.util.Optional;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.persistence.model.product.ProductEntity;

@Component
public class CartItemDaoImpl implements ICartItemDao {

   @Autowired
   private ICartItemRepository cartItemRepository;

    @Override
    public List<CartItemEntity> findAll() {
        // Devuelve todos los registros de mesas desde la base de datos.
        return  (List<CartItemEntity>) this.cartItemRepository.findAll();    }

    @Override
    public Optional<CartItemEntity> findById(Long id) {
        // Devuelve un registro de mesa basado en su ID, si existe.
        return this.cartItemRepository.findById(id);
    }

    @Override
    public CartItemEntity save(CartItemEntity entity) {
        // Guarda o actualiza la mesa en la base de datos y retorna el objeto persistido.
        return this.cartItemRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de mesa basado en su ID.
        this.cartItemRepository.deleteById(id);
    }

    @Override
    public List<CartItemEntity> findByCart(CartEntity cartEntity) {
        return this.cartItemRepository.findByCart(cartEntity);
    }

    @Override
    public Optional<CartItemEntity> findByCartAndProduct(CartEntity cartEntity, ProductEntity productEntity) {
        return this.cartItemRepository.findByCartAndProduct(cartEntity, productEntity);
    }

    @Override
    public void deleteAll(List<CartItemEntity> items) {
        this.cartItemRepository.deleteAll(items);
    }
}
