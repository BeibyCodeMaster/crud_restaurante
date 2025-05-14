package restaurante.example.demo.presentation.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.presentation.dto.role.RoleDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Objeto de Transferencia de Datos (DTO) que representa la información de un usuario en el sistema.
// Este DTO se utiliza para gestionar los datos del usuario, que puede ser administrador, cliente, empleado, y super.
public class UserDto {

    // Identificador único del usuario
    private Long id;

    // Nombre del usuario. No puede estar vacío.
    @NotBlank(message = "Ingrese el nombre.")
    private String firstName;

    // Apellido del usuario. No puede estar vacío.
    @NotBlank(message = "Ingrese el apellido.")
    private String lastName;

    // Correo electrónico del usuario. No puede estar vacío y debe tener un formato válido.
    @NotBlank(message = "Ingrese el correo.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    private String email;

    // Dirección del usuario. No puede estar vacía.
    @NotBlank(message = "Ingrese la dirección.")
    private String address;

    // Teléfono del usuario. No puede estar vacío.
    @NotBlank(message = "Ingrese el teléfono.")
    private String phone;

    // Contraseña del usuario. No puede estar vacía.
    @NotBlank(message = "Ingrese la clave.")
    private String password;

    // Nombre de usuario. No puede estar vacío.
    @NotBlank(message = "Ingrese su usuario.")
    private String userName;

    // Fecha de nacimiento del usuario. No puede ser nula y debe ser en el pasado.
    @NotNull(message = "Ingrese la fecha de cumpleaños.")
    @Past(message = "La fecha de cumpleaños debe ser en el pasado.")
    private LocalDate birthDate;

    // Fecha de creación del registro del usuario.
    private LocalDateTime createdAt;

    // Fecha de última actualización del registro.
    private LocalDateTime updatedAt;
    
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
        
    // Lista de roles asignados al usuario (por ejemplo, administrador, cliente).
    private Set<String> roles;
}
