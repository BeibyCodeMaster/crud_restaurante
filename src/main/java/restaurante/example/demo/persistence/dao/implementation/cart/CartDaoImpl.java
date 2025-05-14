package restaurante.example.demo.persistence.dao.implementation.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import restaurante.example.demo.persistence.dao.interfaces.cart.ICartDao;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.persistence.repositoy.cart.ICartRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CartDaoImpl implements ICartDao {

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public List<CartEntity> findAll() {
        // Devuelve todos los registros de carritos desde la base de datos.
        return  (List<CartEntity>) this.cartRepository.findAll();    }

    @Override
    public Optional<CartEntity> findById(Long id) {
        // Devuelve un registro de carrito basado en su ID, si existe.
        return this.cartRepository.findById(id);
    }

    @Override
    public CartEntity save(CartEntity entity) {
        // Guarda o actualiza el carrito en la base de datos y retorna el objeto persistido.
        return this.cartRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        // Elimina un registro de carrito basado en su ID.
        this.cartRepository.deleteById(id);
    }

    @Override
    public Optional<CartEntity> findByCustomerId(Long id) {
      return  this.cartRepository.findByCustomerId(id);
    }
}
