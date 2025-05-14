package restaurante.example.demo.exception;

import org.springframework.dao.DataAccessException;

// Clase que maneja los errores ocurridos durante la persistencia de datos.
// Esta clase extiende `DataAccessException` para representar un error específico
// cuando no se puede acceder o manipular los datos correctamente en la base de datos.
public class EntityDataAccesException extends DataAccessException {

    // Constructor que recibe el mensaje de error y lo pasa a la clase base.
    public EntityDataAccesException(String msg) {
        super(msg);
    }
}