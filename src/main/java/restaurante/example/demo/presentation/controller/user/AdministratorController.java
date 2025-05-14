package restaurante.example.demo.presentation.controller.user;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityIllegalArgumentException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.presentation.dto.MessageResponseDto;
import restaurante.example.demo.presentation.dto.auth.AuthResponse;
import restaurante.example.demo.presentation.dto.user.AdministratorDto;
import restaurante.example.demo.service.implementation.user.UserDetailServiceImpl;
import restaurante.example.demo.service.interfaces.user.IAdministratorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
// Controlador encargado de procesar las peticiones relacionadas con los administradores
public class AdministratorController {

    @Autowired
    private IAdministratorService administratorService;  // Interfaz de servicio para manejar la lógica de negocio relacionada con los administradores

    @Autowired
    private UserDetailServiceImpl userDetailService;

    /**
     * Obtener todos los administradores.
     * Método que gestiona las peticiones GET para recuperar todos los administradores registrados.
     *
     * @return ResponseEntity con un mensaje y una lista de administradores o un mensaje de error si no hay administradores.
     */
    @GetMapping("/find/all")
    public ResponseEntity<?>  getAllAdministrators(){
        List<AdministratorDto> administratorDtoList = this.administratorService.getAll();
        // Verifica si la lista de administradores está vacía y devuelve una respuesta NOT_FOUND en ese caso
        if(administratorDtoList.isEmpty()){
            return new ResponseEntity<>(  // Respuesta con código de estado 404 si no se encontraron administradores
                    MessageResponseDto.builder()
                            .message("No hay administradores registrados.")  // Mensaje informando que no hay administradores
                            .build()
                    ,HttpStatus.NOT_FOUND);
        }
        // Devuelve una respuesta con código de estado 200 junto con la lista de administradores
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito.")  // Mensaje indicando que la búsqueda fue exitosa
                        .object(administratorDtoList)  // Lista de administradores encontrados
                        .build()
                ,HttpStatus.OK);
    }

    /**
     * Obtener administrador por ID.
     * Método para buscar un administrador especificado por su ID.
     *
     * @param id Identificador del administrador
     * @return ResponseEntity con un mensaje y el objeto AdministratorDto encontrado
     * @throws EntityNotFoundException si el administrador no se encuentra
     */
    @GetMapping("/find/{id}")
    private ResponseEntity<?> findAdministratorById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);  // Validación del ID
        AdministratorDto administratorDto = this.administratorService.getOneById(id);
        // Respuesta con el administrador encontrado
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizó la búsqueda con éxito.")  // Mensaje indicando que la búsqueda fue exitosa
                        .object(administratorDto)  // Administrador encontrado
                        .build()
                ,HttpStatus.OK);
    }

    /**
     * Crear nuevo administrador.
     * Método para manejar las peticiones POST y registrar un nuevo administrador.
     *
     * @param administratorDto Objeto AdministratorDto con los datos del nuevo administrador
     * @return ResponseEntity con un mensaje y el administrador creado.
     * @throws EntityDataAccesException si ocurre algún error durante la creación
     */
    @PostMapping("/signup")
    private ResponseEntity<?>  createAdministrator(@Valid @RequestBody AdministratorDto administratorDto) throws EntityDataAccesException {
        AdministratorDto adminDtoCreated = this.administratorService.create(administratorDto);
        AuthResponse authResponse = this.userDetailService.createTokenForRegisteredUser(adminDtoCreated.getUser().getUserName());
        // Respuesta con el administrador creado
        return new ResponseEntity<>(
                 authResponse
                ,HttpStatus.CREATED);
    }

    /**
     * Actualizar administrador existente.
     * Método que maneja las peticiones PUT para actualizar un administrador especificado por ID.
     *
     * @param id Identificador del administrador a actualizar
     * @param administratorDto Objeto AdministratorDto con los datos actualizados
     * @return ResponseEntity con un mensaje y el objeto actualizado
     * @throws EntityDataAccesException si ocurre algún error de acceso a datos
     * @throws EntityNotFoundException si el administrador no se encuentra
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdministrator(@PathVariable Long id, @Valid  @RequestBody AdministratorDto administratorDto) throws EntityNotFoundException, EntityDataAccesException {
        validateId(id);  // Validación del ID
        AdministratorDto adminDtoUpdated = this.administratorService.update(id,administratorDto);
        // Respuesta con el administrador actualizado
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("El administrador se actualizó correctamente.")  // Mensaje de confirmación
                        .object(adminDtoUpdated)  // Administrador actualizado
                        .build()
                , HttpStatus.OK);
    }

    /**
     * Eliminar administrador por ID.
     * Método para manejar las peticiones DELETE y eliminar un administrador por su ID.
     *
     * @param id Identificador del administrador a eliminar
     * @return ResponseEntity con un mensaje de confirmación de eliminación
     * @throws EntityNotFoundException si el administrador no se encuentra
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdministrator(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);  // Validación del ID
        String msgService = this.administratorService.delete(id);
        // Respuesta con el mensaje de eliminación
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message(msgService)  // Mensaje de confirmación de eliminación
                        .build()
                ,HttpStatus.OK);
    }

    /**
     * Método auxiliar para validar el ID.
     * Valida que el ID sea positivo y no nulo, lanzando una excepción si no cumple estos criterios.
     *
     * @param id Identificador a validar
     * @throws EntityIllegalArgumentException si el ID es inválido
     */
    private void validateId(Long id) throws EntityIllegalArgumentException {
        if (id == null || id.intValue() <= 0) {
            throw new EntityIllegalArgumentException("El ID debe ser un número positivo y mayor que cero.");
        }
    }

}
