package restaurante.example.demo.presentation.controller.order;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityIllegalArgumentException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.presentation.dto.MessageResponseDto;
import restaurante.example.demo.presentation.dto.order.OrderDto;
import restaurante.example.demo.service.interfaces.order.IOrderService;

@RestController
@RequestMapping("/api/v1/order")
// Controlador encargado de procesar las peticiones relacionadas con las órdenes
public class OrderController {

    @Autowired
    private IOrderService orderService; // Interfaz de servicio para manejar la lógica de negocio relacionada con las órdenes

    /**
     * Obtener todas las órdenes.
     * Método que gestiona las peticiones GET para recuperar todas las órdenes registradas.
     *
     * @return ResponseEntity con un mensaje y una lista de órdenes o un mensaje de error si no hay órdenes
     */
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllOrders() {
        List<OrderDto> ordersDto = this.orderService.getAll();

        // Verifica si la lista de órdenes está vacía y devuelve una respuesta NOT_FOUND en ese caso
        if (ordersDto.isEmpty()) {
            return new ResponseEntity<>(
                    MessageResponseDto.builder()
                            .message("No hay órdenes registradas.")
                            .build(), HttpStatus.NOT_FOUND);
        }

        // Devuelve una respuesta OK junto con la lista de órdenes encontrada
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito.")
                        .object(ordersDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Obtener orden por ID.
     * Método para buscar una orden específica por su ID.
     *
     * @param id Identificador de la orden
     * @return ResponseEntity con un mensaje y el objeto OrderDto encontrado
     * @throws EntityNotFoundException si la orden no se encuentra
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validación del ID
        OrderDto orderDto = this.orderService.getOneById(id);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito.")
                        .object(orderDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Crear nueva orden.
     * Método para manejar las peticiones POST y registrar una nueva orden.
     *
     * @param orderDto Objeto OrderDto con los datos de la nueva orden
     * @return ResponseEntity con un mensaje y la orden creada
     * @throws EntityDataAccesException si ocurre algún error durante la creación
     */
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) throws EntityDataAccesException {
        OrderDto createdOrderDto = this.orderService.create(orderDto);

        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La orden se registró correctamente.")
                        .object(createdOrderDto)
                        .build(), HttpStatus.CREATED);
    }

    /**
     * Actualizar orden existente.
     * Método que maneja las peticiones PUT para actualizar una orden específica por ID.
     *
     * @param id Identificador de la orden a actualizar
     * @param orderDto Objeto OrderDto con los datos actualizados
     * @return ResponseEntity con un mensaje y el objeto actualizado
     * @throws EntityDataAccesException si ocurre algún error de acceso a datos
     * @throws EntityNotFoundException si la orden no se encuentra
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto orderDto) throws EntityDataAccesException, EntityNotFoundException {
        validateId(id); // Validación del ID

        OrderDto updatedOrderDto = this.orderService.update(id, orderDto);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La orden se actualizó correctamente.")
                        .object(updatedOrderDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Eliminar orden por ID.
     * Método para manejar las peticiones DELETE y eliminar una orden por su ID.
     *
     * @param id Identificador de la orden a eliminar
     * @return ResponseEntity con un mensaje de confirmación de eliminación
     * @throws EntityNotFoundException si la orden no se encuentra
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validación del ID

        String responseMessage = this.orderService.delete(id);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message(responseMessage)
                        .build(), HttpStatus.OK);
    }

    /**
     * Método auxiliar para validar el ID.
     * Valida que el ID sea positivo y no nulo, lanzando una excepción si no cumple estos criterios.
     *
     * @param id Identificador a validar
     * @throws EntityIllegalArgumentException si el ID es inválido
     */
    private void validateId(Long id) throws EntityIllegalArgumentException {
        if (id == null || id <= 0) {
            throw new EntityIllegalArgumentException("ID debe ser un número positivo y mayor que cero");
        }
    }
}
