package restaurante.example.demo.presentation.dto.cart;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.presentation.dto.product.ProductDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private BigDecimal value;
    private byte amount;
    private ProductDto product;
    private CartDto cart;
}
