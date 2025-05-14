package restaurante.example.demo.presentation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
// Objeto de Transferencia de Datos (DTO) utilizado para enviar respuestas al frontend.
// Este DTO proporciona un mensaje de respuesta junto con un objeto adicional opcional.
public class MessageResponseDto implements Serializable {
    private String message; // Mensaje de respuesta
    private Object object;  // Objeto adicional que se incluye en la respuesta, puede ser cualquier tipo de dato.
}
