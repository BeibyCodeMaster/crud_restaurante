package restaurante.example.demo.service.interfaces.cart;

import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.cart.CartDto;
import restaurante.example.demo.presentation.dto.order.OrderDto;

public interface ICartService {
    CartDto addProductsToCart(CartDto entityDto);
    CartDto getCartByCustomer(Long userId);
    CartDto getCartById(Long id);
    CartDto updateCart(Long id, CartDto cartDto)  throws EntityNotFoundException;
    String deleteCart(Long id) throws EntityNotFoundException;
    OrderDto checkoutCart(Long cartId) throws EntityNotFoundException;
    CartEntity createCart(CustomerEntity customer);
}
