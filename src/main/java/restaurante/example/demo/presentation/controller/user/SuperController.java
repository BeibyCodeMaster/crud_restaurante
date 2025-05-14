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
import restaurante.example.demo.presentation.dto.user.SuperDto;
import restaurante.example.demo.service.implementation.user.UserDetailServiceImpl;
import restaurante.example.demo.service.interfaces.user.ISuperService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/super")
public class SuperController {

    @Autowired
    private ISuperService superService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/find/all")
    public ResponseEntity<?> getAllSupers(){
        List<SuperDto> superDtoList = this.superService.getAll();
        if(superDtoList.isEmpty()){
            return new ResponseEntity<>(
                            MessageResponseDto.builder()
                                .message("No hay usuarios registrados.")
                                .build()
                            ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                    MessageResponseDto.builder()
                            .message("Se realizo la busqueda con exito.")
                            .object(superDtoList)
                            .build()
                    ,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findSuperById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        SuperDto superDto = this.superService.getOneById(id);
        return new ResponseEntity<>(
                        MessageResponseDto.builder()
                            .message("Se realizo la busqueda con exito.")
                            .object(superDto)
                            .build()
                        ,HttpStatus.OK);
    }

    @PostMapping("/signup")
    private ResponseEntity<?>  createSuper(@Valid @RequestBody SuperDto superDto) throws EntityDataAccesException {
        System.out.println("Controlador :" + superDto);
        SuperDto superDtoCreated = this.superService.create(superDto);
        AuthResponse authResponse = this.userDetailService.createTokenForRegisteredUser(superDtoCreated.getUser().getUserName());
        return new ResponseEntity<>(
                         authResponse
                        ,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSuper(@PathVariable Long id, @Valid @RequestBody SuperDto superDto) throws EntityNotFoundException,  EntityDataAccesException {
        validateId(id);
        SuperDto superDtoUpdate = this.superService.update(id,superDto);
        return new ResponseEntity<>(
                        MessageResponseDto.builder()
                                .message("El usuario se actualizo correctamente.")
                                .object(superDtoUpdate)
                                .build()
                        , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSuper(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        String msgService = this.superService.delete(id);
        return new ResponseEntity<>(
                            MessageResponseDto.builder()
                                    .message(msgService)
                                    .build()
                            ,HttpStatus.OK);
    }

    // Método auxiliar para validar el ID
    private void validateId(Long id) throws EntityIllegalArgumentException {
        if (id == null || id.intValue() <= 0  ) {
            throw new EntityIllegalArgumentException("El ID debe ser un número positivo y mayor que cero.");
        }
    }

}
