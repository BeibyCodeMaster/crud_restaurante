package restaurante.example.demo.persistence.model.cart;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurante.example.demo.persistence.enums.MesaLocationEnum;
import restaurante.example.demo.persistence.enums.MesaStateEnum;
import restaurante.example.demo.persistence.model.product.ProductEntity;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carrito_item")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrito_item_id")
    private Long id;

    @Column(name = "valor", nullable = false)
    private BigDecimal value;
    
    @Column(name = "cantidad", nullable = false)
    private byte amount;

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
    @JoinColumn(name = "producto_id", foreignKey = @ForeignKey(name = "fk_item_cart_producto"))
    private ProductEntity product;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn( name = "carrito_id" , foreignKey = @ForeignKey(name = "fk_cart_producto"))
    private CartEntity cart;

}
