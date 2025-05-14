package restaurante.example.demo.presentation.controller.cart;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurante.example.demo.exception.EntityIllegalArgumentException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.presentation.dto.MessageResponseDto;
import restaurante.example.demo.presentation.dto.cart.CartDto;
import restaurante.example.demo.presentation.dto.order.OrderDto;
import restaurante.example.demo.service.interfaces.cart.ICartService;


// Anotación para indicar que esta clase es un controlador REST que maneja las peticiones HTTP
@RestController
// Define el prefijo de la URL para las rutas gestionadas por este controlador
@RequestMapping("/api/v1/cart")
public class CartController {

    // Inyección de dependencias para el servicio que gestiona la lógica de negocio relacionada con los carritos
    @Autowired
    private ICartService cartService;

    /**
     * Obtener el carrito de compras de un cliente específico.
     *
     * @param id ID del cliente cuyo carrito se desea obtener.
     * @return ResponseEntity con un mensaje y los detalles del carrito.
     */
    @RequestMapping("/find/user/{id}")
    public ResponseEntity<?> getCartByCustomer(@PathVariable Long id) {
        validateId(id); // Validar que el ID sea válido
        CartDto cartDto = this.cartService.getCartByCustomer(id); // Obtener carrito por cliente
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Carrito obtenido con éxito.") // Mensaje de éxito
                        .object(cartDto) // Detalles del carrito
                        .build(), HttpStatus.OK // Código de respuesta HTTP
        );
    }

    /**
     * Obtener un carrito específico por su ID.
     *
     * @param id ID del carrito que se desea obtener.
     * @return ResponseEntity con un mensaje y los detalles del carrito.
     */
    @RequestMapping("/find/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        validateId(id); // Validar que el ID sea válido
        CartDto cartDto = this.cartService.getCartById(id); // Obtener carrito por ID
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Carrito obtenido con éxito.") // Mensaje de éxito
                        .object(cartDto) // Detalles del carrito
                        .build(), HttpStatus.OK // Código de respuesta HTTP
        );
    }

    /**
     * Agregar productos al carrito.
     *
     * @param cartDto Objeto CartDto con los datos del cliente y productos a agregar.
     * @return ResponseEntity con un mensaje y el carrito actualizado.
     */
    @RequestMapping("/add")
    public ResponseEntity<?> addItemsToCart(@RequestBody CartDto cartDto) {
        CartDto updatedCartDto = this.cartService.addProductsToCart(cartDto); // Agregar productos al carrito
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Productos agregados al carrito exitosamente.") // Mensaje de éxito
                        .object(updatedCartDto) // Detalles del carrito actualizado
                        .build(), HttpStatus.OK // Código de respuesta HTTP
        );
    }

    /**
     * Actualizar los datos de un carrito existente.
     *
     * @param cartId ID del carrito a actualizar.
     * @param cartDto Objeto CartDto con los nuevos datos.
     * @return ResponseEntity con el carrito actualizado.
     */
    @RequestMapping("/update/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Long cartId, @Valid @RequestBody CartDto cartDto) throws EntityNotFoundException {
        validateId(cartId); // Validar que el ID sea válido
        CartDto updatedCartDto = this.cartService.updateCart(cartId, cartDto); // Actualizar carrito
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Carrito actualizado correctamente.") // Mensaje de éxito
                        .object(updatedCartDto) // Detalles del carrito actualizado
                        .build(), HttpStatus.OK // Código de respuesta HTTP
        );
    }

    /**
     * Eliminar un carrito.
     *
     * @param id ID del carrito a eliminar.
     * @return ResponseEntity con un mensaje de confirmación.
     */
    @RequestMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validar que el ID sea válido
        String responseMessage = this.cartService.deleteCart(id); // Eliminar carrito
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message(responseMessage) // Mensaje de confirmación
                        .build(), HttpStatus.OK // Código de respuesta HTTP
        );
    }

    /**
     * Realizar el checkout del carrito y generar una orden.
     *
     * @param id ID del carrito a procesar.
     * @return ResponseEntity con los detalles de la orden creada.
     */
    @PostMapping("/checkout/{id}")
    public ResponseEntity<?> checkoutCart(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validar que el ID sea válido
        OrderDto orderDto = this.cartService.checkoutCart(id); // Realizar checkout y generar orden
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Checkout realizado con éxito. Orden creada.") // Mensaje de éxito
                        .object(orderDto) // Detalles de la orden
                        .build(), HttpStatus.CREATED // Código de respuesta HTTP
        );
    }

    /**
     * Validar que un ID sea positivo y no nulo.
     *
     * @param id ID a validar.
     * @throws EntityIllegalArgumentException si el ID es inválido.
     */
    private void validateId(Long id) throws EntityIllegalArgumentException {
        if (id == null || id <= 0) {
            throw new EntityIllegalArgumentException("ID debe ser un número positivo y mayor que cero"); // Error si el ID es inválido
        }
    }
}
