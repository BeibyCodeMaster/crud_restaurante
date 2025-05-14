package restaurante.example.demo.presentation.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.presentation.dto.user.CustomerDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long id;// Identificador único de la mesa
    private Integer numberOfPeople;
    private LocalTime availableTime;
    private LocalDate date;
    private String description;
    private CustomerDto customer;
    private ReservationStatusDto reservationStatus;
    private Set<MesaDto> mesas; // Relación con mesas (ManyToMany)
    private Long idMesa; // mesa selecionada
}
