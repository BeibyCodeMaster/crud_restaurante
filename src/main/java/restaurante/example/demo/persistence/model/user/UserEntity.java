package restaurante.example.demo.persistence.model.user;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurante.example.demo.persistence.model.role.RoleEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id; // Identificador único del usuario. Se genera automáticamente.

    @Column(name = "nombre", nullable = false)
    private String firstName; // Nombre del usuario. No puede ser nulo.

    @Column(name = "apellido", nullable = false)
    private String lastName; // Apellido del usuario. No puede ser nulo.

    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String email; // Correo electrónico del usuario. Es único y no puede ser nulo.

    @Column(name = "direccion")
    private String address; // Dirección del usuario.

    @Column(name = "telefono")
    private String phone; // Número de teléfono del usuario.

    @Column(name = "clave", nullable = false)
    private String password; // Contraseña del usuario. No puede ser nula.

    @Column(unique = true,  name = "nombre_usuario", nullable = false)
    private String userName; // Nombre de usuario. No puede ser nulo.

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate; // Fecha de nacimiento del usuario.

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  // Fecha de creación del usuario.

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // Fecha de última actualización del usuario.

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;
    
    @Column(name = "account_no_locked")
    private boolean accountNoLocked;
    
    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="usuario_rol",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="rol_id")
    )
    private Set<RoleEntity> roles; // Lista de roles asignados al usuario. Relación muchos a muchos con RoleEntity.
}


