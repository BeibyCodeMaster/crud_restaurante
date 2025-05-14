package restaurante.example.demo.persistence.model.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.user.CustomerDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserva")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer id;

    @Column(name = "numero_personas", nullable = false)
    @NotNull
    @Min(1)
    private Integer numberOfPeople;

    @Column(name = "hora_disponible", nullable = false)
    private LocalTime availableTime;

    @Column(name = "fecha", nullable = false)
    private LocalDate date;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(
        cascade = CascadeType.PERSIST,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "cliente_id")
    private CustomerEntity customer;

    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "estado_id")
    private ReservationStatusEntity reservationStatus;

    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "reserva_mesa",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name="mesa_id")
    )
    private Set<MesaEntity> mesas;

}

