package restaurante.example.demo.exception;

// Clase que maneja las excepciones de argumentos inválidos.
// Extiende `IllegalArgumentException` y se utiliza para lanzar excepciones
// cuando los parámetros proporcionados no son válidos o no cumplen con los requisitos.
public class EntityIllegalArgumentException extends IllegalArgumentException {

    // Constructor que recibe el mensaje de error y lo pasa a la clase base.
    public EntityIllegalArgumentException(String message) {
        super(message);
    }
}
