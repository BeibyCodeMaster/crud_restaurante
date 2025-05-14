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
import restaurante.example.demo.presentation.dto.user.EmployeeDto;
import restaurante.example.demo.service.implementation.user.UserDetailServiceImpl;
import restaurante.example.demo.service.interfaces.user.IEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/find/all")
    public ResponseEntity<?> getAllEmployees(){
        List<EmployeeDto> employeeDtoList = this.employeeService.getAll();
        if(employeeDtoList.isEmpty()){
            return new ResponseEntity<>(
                            MessageResponseDto.builder()
                                    .message("No hay empleados registrados.")
                                    .build()
                            ,HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<>(
                        MessageResponseDto.builder()
                                .message("Se realizo la busqueda con exito.")
                                .object(employeeDtoList)
                                .build()
                        ,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findEmployeeById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        EmployeeDto employeeDto = this.employeeService.getOneById(id);
        return new ResponseEntity<>(
                        MessageResponseDto.builder()
                                .message("Se realizo la busqueda con exito.")
                                .object(employeeDto)
                                .build()
                        ,HttpStatus.OK);
    }

    @PostMapping("/signup")
    private ResponseEntity<?>  createEmployee(@Valid @RequestBody EmployeeDto employeeDto)  throws EntityDataAccesException {
        System.out.println("Controlador :" + employeeDto);
        EmployeeDto employeeDtoCreated = this.employeeService.create(employeeDto);
        AuthResponse authResponse = this.userDetailService.createTokenForRegisteredUser(employeeDtoCreated.getUser().getUserName());
        return new ResponseEntity<>(
                         authResponse
                        ,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) throws EntityNotFoundException, EntityDataAccesException {
        validateId(id);
        EmployeeDto employeeDtoUpdate = this.employeeService.update(id,employeeDto);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("El usuario se actualizo correctamente.")
                        .object(employeeDtoUpdate)
                        .build()
                , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        String msgService = this.employeeService.delete(id);
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
