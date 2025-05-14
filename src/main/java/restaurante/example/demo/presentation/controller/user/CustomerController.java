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
import restaurante.example.demo.presentation.dto.user.CustomerDto;
import restaurante.example.demo.service.implementation.user.UserDetailServiceImpl;
import restaurante.example.demo.service.interfaces.user.ICustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/find/all")
    public ResponseEntity<?> getAllCustomers(){
        List<CustomerDto> customerDtoList = this.customerService.getAll();
        if(customerDtoList.isEmpty()){
            return new ResponseEntity<>(
                            MessageResponseDto.builder()
                                    .message("No hay clientes registrados.")
                                    .build()
                            ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                        MessageResponseDto.builder()
                                .message("Se realizo la busqueda con exito.")
                                .object(customerDtoList)
                                .build()
                        ,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findCustomerById(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        CustomerDto customerDto = this.customerService.getOneById(id);
        return new ResponseEntity<>(
                MessageResponseDto.builder()
                        .message("Se realizo la busqueda con exito.")
                        .object(customerDto)
                        .build()
                ,HttpStatus.OK);
    }

    @PostMapping("/signup")
    private ResponseEntity<?>  createCustomer(@Valid @RequestBody CustomerDto customerDto)  throws EntityDataAccesException {
        System.out.println("Controlador :" + customerDto);
        CustomerDto customerDtoCreated = this.customerService.create(customerDto);
        AuthResponse authResponse = this.userDetailService.createTokenForRegisteredUser(customerDtoCreated.getUser().getUserName());
        return new ResponseEntity<>(
                         authResponse
                        ,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) throws EntityNotFoundException, EntityDataAccesException {
        validateId(id);
        CustomerDto customerDtoUpdate = this.customerService.update(id,customerDto);
        return new ResponseEntity<>(
                        MessageResponseDto.builder()
                                .message("El usuario se actualizo correctamente.")
                                .object(customerDtoUpdate)
                                .build()
                        , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws EntityNotFoundException {
        validateId(id);
        String msgService = this.customerService.delete(id);
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
