package restaurante.example.demo.presentation.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private String number;
    private LocalDate date; // ISO-8601 format (yyyy-MM-dd)
    private String vat;
    private String total;
    private String subtotal;
    private PaymentMethodDto paymentMethodId;
}
