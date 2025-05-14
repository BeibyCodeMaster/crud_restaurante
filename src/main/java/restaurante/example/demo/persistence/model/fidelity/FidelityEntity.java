package restaurante.example.demo.persistence.model.fidelity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurante.example.demo.persistence.enums.StatusEnum;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fidelidad")
public class FidelityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fidelidad_id")
    private Long id;

    @Column(name = "beneficios", nullable = false)
    private String benefits;

    @Column(name = "puntos", nullable = false)
    private Integer points;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private StatusEnum status;

    @Column(name = "fechaDefInicio", nullable = false)
    private LocalDate startDate;

    @Column(name = "fechaDefFin")
    private LocalDate endDate;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "cliente_id")
    private CustomerEntity customer;
}
