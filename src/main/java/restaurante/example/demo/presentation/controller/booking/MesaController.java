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
import restaurante.example.demo.presentation.dto.booking.MesaDto;
import restaurante.example.demo.service.interfaces.booking.IMesaService;

@RestController
@RequestMapping("/api/v1/mesa")
// Controlador encargado de procesar las peticiones relacionadas con las mesas
public class MesaController {

    @Autowired
    private IMesaService mesaService; // Interfaz de servicio para manejar la lógica de negocio relacionada con las mesas

    /**
     * Obtener todas las mesas.
     * Método que gestiona las peticiones GET para recuperar todas las mesas registradas.
     *
     * @return ResponseEntity con un mensaje y una lista de mesas o un mensaje de error si no hay mesas
     */
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllMesas() {
        List<MesaDto> mesasDto = this.mesaService.getAll();

        // Verifica si la lista de mesas está vacía y devuelve una respuesta NOT_FOUND en ese caso
        if (mesasDto.isEmpty()) {
            return new ResponseEntity<>(
                    MessageResponseDto.builder()
                            .message("No hay mesas registradas.")
                            .build(), HttpStatus.NOT_FOUND);
        }

        // Devuelve una respuesta OK junto con la lista de mesas encontrada
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito.")
                        .object(mesasDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Obtener mesa por ID.
     * Método para buscar una mesa específica por su ID.
     *
     * @param id Identificador de la mesa
     * @return ResponseEntity con un mensaje y el objeto MesaDTO encontrado
     * @throws EntityNotFoundException si la mesa no se encuentra
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getMesaById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validación del ID
        MesaDto mesaDto = this.mesaService.getOneById(id);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito")
                        .object(mesaDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Crear nueva mesa.
     * Método para manejar las peticiones POST y registrar una nueva mesa.
     *
     * @param mesaDto Objeto MesaDTO con los datos de la nueva mesa
     * @return ResponseEntity con un mensaje y la mesa creada
     * @throws EntityDataAccesException si ocurre algún error durante la creación
     */
    @PostMapping("/create")
    public ResponseEntity<?> createMesa(@Valid @RequestBody MesaDto mesaDto) throws EntityDataAccesException {
        MesaDto mesaDtoCreated = this.mesaService.create(mesaDto);

        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La mesa se registró correctamente.")
                        .object(mesaDtoCreated)
                        .build(), HttpStatus.CREATED);
    }

    /**
     * Actualizar mesa existente.
     * Método que maneja las peticiones PUT para actualizar una mesa específica por ID.
     *
     * @param id Identificador de la mesa a actualizar
     * @param mesaDto Objeto MesaDTO con los datos actualizados
     * @return ResponseEntity con un mensaje y el objeto actualizado
     * @throws EntityDataAccesException si ocurre algún error de acceso a datos
     * @throws EntityNotFoundException si la mesa no se encuentra
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMesa(@PathVariable Long id, @Valid @RequestBody MesaDto mesaDto) throws EntityDataAccesException, EntityNotFoundException {
        validateId(id); // Validación del ID

        MesaDto updatedMesaDto = this.mesaService.update(id, mesaDto);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("La mesa se actualizó correctamente.")
                        .object(updatedMesaDto)
                        .build(), HttpStatus.OK);
    }

    /**
     * Eliminar mesa por ID.
     * Método para manejar las peticiones DELETE y eliminar una mesa por su ID.
     *
     * @param id Identificador de la mesa a eliminar
     * @return ResponseEntity con un mensaje de confirmación de eliminación
     * @throws EntityNotFoundException si la mesa no se encuentra
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id); // Validación del ID

        String responseMessage = this.mesaService.delete(id);
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
