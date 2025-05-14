package restaurante.example.demo.presentation.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.model.product.ProductEntity;
import restaurante.example.demo.presentation.dto.product.ProductDto;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private BigDecimal value; //Precio unitario del producto en el momento del pedido
    private byte amount; //Cantidad de este producto en el pedido
    private ProductDto product; // producto asociado a item_pedido
    private OrderDto order;// pedido asociado a los item del producto.
}
