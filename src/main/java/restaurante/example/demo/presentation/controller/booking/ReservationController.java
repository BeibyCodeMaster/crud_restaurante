/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.presentation.controller.booking;

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
import restaurante.example.demo.presentation.dto.booking.ReservationDto;
import restaurante.example.demo.service.interfaces.booking.IReservationService;

@RestController
@RequestMapping("/api/v1/reservation")
// Controlador encargado de procesar las peticiones relacionadas con las reservas
public class ReservationController {

    @Autowired
    private IReservationService reservationService; // Interfaz de servicio para manejar la lógica de negocio relacionada con las reservas

    /**
     * Obtener todas las reservas.
     * Método que gestiona las peticiones GET para recuperar todas las reservas registradas.
     *
     * @return ResponseEntity con un mensaje y una lista de reservas o un mensaje de error si no hay reservas
     */
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllReservations() {
        List<ReservationDto> reservationsDto = this.reservationService.getAll();

        // Verifica si la lista de reservas está vacía y devuelve una respuesta NOT_FOUND en ese caso
        if (reservationsDto.isEmpty()) {
            return new ResponseEntity<>(
                    MessageResponseDto.builder()
                            .message("No hay reservas registradas.")
                            .build(), HttpStatus.NOT_FOUND);
        }

        // Devuelve una respuesta OK junto con la lista de reservas encontrada
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito.")
                        .object(reservationsDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Obtener reserva por ID.
     * Método para buscar una reserva específica por su ID.
     *
     * @param id Identificador de la reserva
     * @return ResponseEntity con un mensaje y el objeto ReservationDto encontrado
     * @throws EntityNotFoundException si la reserva no se encuentra
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validación del ID
        ReservationDto reservationDto = this.reservationService.getOneById(id);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito")
                        .object(reservationDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Crear nueva reserva.
     * Método para manejar las peticiones POST y registrar una nueva reserva.
     *
     * @param reservationDto Objeto ReservationDto con los datos de la nueva reserva
     * @return ResponseEntity con un mensaje y la reserva creada
     * @throws EntityDataAccesException si ocurre algún error durante la creación
     */
    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDto reservationDto) throws EntityDataAccesException {
        ReservationDto reservationDtoCreated = this.reservationService.create(reservationDto);

        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La reserva se registró correctamente.")
                        .object(reservationDtoCreated)
                        .build(), HttpStatus.CREATED);
    }

    /**
     * Actualizar reserva existente.
     * Método que maneja las peticiones PUT para actualizar una reserva específica por ID.
     *
     * @param id Identificador de la reserva a actualizar
     * @param reservationDto Objeto ReservationDto con los datos actualizados
     * @return ResponseEntity con un mensaje y el objeto actualizado
     * @throws EntityDataAccesException si ocurre algún error de acceso a datos
     * @throws EntityNotFoundException si la reserva no se encuentra
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDto reservationDto) throws EntityDataAccesException, EntityNotFoundException {
        validateId(id); // Validación del ID

        ReservationDto updatedReservationDto = this.reservationService.update(id, reservationDto);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La reserva se actualizó correctamente.")
                        .object(updatedReservationDto)
                        .build(), HttpStatus.OK);
    }
    
    /**
     * Cancelar reserva.
     * Método para manejar las peticiones PUT y cancelar una reserva específica por ID.
     *
     * @param id Identificador de la reserva a cancelar
     * @return ResponseEntity con un mensaje y la reserva actualizada
     * @throws EntityNotFoundException si la reserva no se encuentra
    */
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        ReservationDto canceledReservation = this.reservationService.cancel(id);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La reserva fue cancelada correctamente.")
                        .object(canceledReservation)
                        .build(), HttpStatus.OK);
    }

    /**
     * Eliminar reserva por ID.
     * Método para manejar las peticiones DELETE y eliminar una reserva por su ID.
     *
     * @param id Identificador de la reserva a eliminar
     * @return ResponseEntity con un mensaje de confirmación de eliminación
     * @throws EntityNotFoundException si la reserva no se encuentra
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validación del ID

        String responseMessage = this.reservationService.delete(id);
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
