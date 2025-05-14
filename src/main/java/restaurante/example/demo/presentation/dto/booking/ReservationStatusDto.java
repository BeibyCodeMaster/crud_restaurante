package restaurante.example.demo.presentation.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.ReservationDetailsEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationStatusDto {
    private Long id;
    private ReservationDetailsEnum status; // CONFIRMADA, CANCELADA, PENDIENTE
    private String description;
}
