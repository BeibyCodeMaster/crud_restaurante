package restaurante.example.demo.presentation.dto.fidelity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.user.CustomerDto;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FidelityDto {
    private Long id;
    private String benefits;
    private Integer points;
    private String status; // 'activo' or 'inactivo'
    private LocalDate startDate;
    private LocalDate endDate;
    private CustomerEntity customer;
}
