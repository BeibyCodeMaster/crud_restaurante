package restaurante.example.demo.presentation.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithQuantityDto {
    private Long productId;
    private Long quantity;
}

