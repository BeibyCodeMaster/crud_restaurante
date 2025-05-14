package restaurante.example.demo.presentation.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.OrderStateEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private Long id;
    private OrderStateEnum status; // PENDIENTE, COMPLETADO, CANCELADO
    private String description;
}
