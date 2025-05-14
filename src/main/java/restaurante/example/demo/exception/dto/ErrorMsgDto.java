/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// Objeto de Transferencia de Datos (DTO) que representa un error dentro del sistema.
// Contiene el estado HTTP y el mensaje de error que se devolverá al cliente.
public class ErrorMsgDto {
    HttpStatus status;  // Estado HTTP que representa el tipo de error (ej. 404, 500, etc.)
    String message;     // Mensaje de error descriptivo para el usuario o cliente
}
