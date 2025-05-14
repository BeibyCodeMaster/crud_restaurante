package restaurante.example.demo.persistence.model.user;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.model.common.DateTimeActive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="administrador")
public class AdministratorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="administrador_id")
    private Long id; // Identificador único del administrador. Se genera automáticamente.

    @Embedded
    private DateTimeActive dateTimeActive; // Información relacionada con la fecha y estado de activación del administrador.

    @OneToOne(
            targetEntity = UserEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "usuario_id")
    private UserEntity user; // Relación uno a uno con la entidad UserEntity. Representa al usuario asociado al administrador.
}
