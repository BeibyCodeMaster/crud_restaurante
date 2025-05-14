package restaurante.example.demo.persistence.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurante.example.demo.persistence.model.product.ProductEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_pedido")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_pedido_id")
    private Long id;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal value; //Precio unitario del producto en el momento del pedido
    
    @Column(name = "cantidad", nullable = false)
    private byte amount; //Cantidad de este producto en el pedido

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  // Fecha de creación del usuario.

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // Fecha de última actualización del usuario.

    @ManyToOne(
        cascade = CascadeType.PERSIST,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "producto_id", foreignKey = @ForeignKey(name = "fk_item_pedido_producto"))
    private ProductEntity product; // producto asociado a item_pedido

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
    private OrderEntity order; // pedido asociado a los item del producto.

}
