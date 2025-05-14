package restaurante.example.demo.persistence.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factura")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Long id;

    @Column(name = "numero", nullable = false, unique = true, length = 15)
    private String number;

    @Column(name = "fecha", nullable = false)
    private LocalDate date;

    @Column(name = "iva", nullable = false, precision = 10, scale = 2)
    private BigDecimal vat;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  // Fecha de creación del usuario.

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // Fecha de última actualización del usuario.

    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "metodo_pago_id", foreignKey = @ForeignKey(name = "fk_factura_metodo_pago"))
    private PaymentMethodEntity paymentMethod;

}
