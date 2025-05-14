
package restaurante.example.demo.exception;

// Clase que maneja las excepciones cuando no se encuentra un recurso solicitado.
// Esta clase extiende `Exception` y se lanza cuando una entidad no es encontrada
// en la base de datos o en cualquier otro proceso de recuperación de recursos.
public class EntityNotFoundException extends Exception {

    // Constructor que recibe el mensaje de error y lo pasa a la clase base.
    public EntityNotFoundException(String message) {
        super(message);
    }
}
