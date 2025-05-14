package restaurante.example.demo.persistence.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metodo_pago")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metodo_pago_id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @Column(name = "otros_detalles")
    private String otherDetails;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  // Fecha de creación del usuario.

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // Fecha de última actualización del usuario.

}
