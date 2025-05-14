package restaurante.example.demo.presentation.dto.fidelity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.presentation.dto.user.CustomerDto;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FidelityTransactionDto {
    private Long id;
    private Integer points;
    private LocalDate date;
    private String description;
    private CustomerDto customer;
}
