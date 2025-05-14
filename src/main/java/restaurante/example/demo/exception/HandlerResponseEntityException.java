/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import restaurante.example.demo.exception.dto.ErrorMsgDto;



@ControllerAdvice
// Controlador que maneja las excepciones globales ocurridas dentro del sistema.
// @ControllerAdvice` permite gestionar las excepciones en toda la aplicación de manera centralizada.
public class HandlerResponseEntityException extends ResponseEntityExceptionHandler {

    // Maneja excepciones de tipo `EntityNotFoundException` y devuelve un error 404 (Not Found).
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMsgDto> entityNotFoundException(EntityNotFoundException exception) {
        // Se crea un DTO con el estado de error y el mensaje asociado.
        ErrorMsgDto errorDto = new ErrorMsgDto(HttpStatus.NOT_FOUND, exception.getMessage());
        // Devuelve una respuesta con el error 404 y el mensaje correspondiente.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    // Maneja excepciones de tipo `EntityDataAccesException` y devuelve un error 500 (Internal Server Error).
    @ExceptionHandler(EntityDataAccesException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMsgDto> entityDataAccesException(EntityDataAccesException exception) {
        // Se crea un DTO con el estado de error y el mensaje asociado.
        ErrorMsgDto errorDto = new ErrorMsgDto(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        // Devuelve una respuesta con el error 500 y el mensaje correspondiente.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    // Maneja excepciones de tipo `EntityIllegalArgumentException` y devuelve un error 400 (Bad Request).
    @ExceptionHandler(EntityIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMsgDto> entityIllegalArgumentException(EntityIllegalArgumentException exception) {
        // Se crea un DTO con el estado de error y el mensaje asociado.
        ErrorMsgDto errorDto = new ErrorMsgDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        // Devuelve una respuesta con el error 400 y el mensaje correspondiente.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(AutBadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMsgDto>  autBadCredentialsException(AutBadCredentialsException exception){
        // Se crea un DTO con el estado de error y el mensaje asociado.
        ErrorMsgDto errorDto = new ErrorMsgDto(HttpStatus.BAD_REQUEST, exception.getMessage());
        // Devuelve una respuesta con el error 400 y el mensaje correspondiente.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
        
    // Maneja las excepciones relacionadas con la validación de los argumentos del método.
    // Este método captura los errores de validación de campos (por ejemplo, validaciones de `@NotNull`, `@Size`, etc.).
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Se crea un mapa para almacenar los errores de validación.
        Map<String, Object> errors = new HashMap<>();
        // Se recorren los errores de los campos y se añaden al mapa.
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        // Devuelve una respuesta con el error 400 y los errores de validación.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
