package restaurante.example.demo.presentation.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.presentation.dto.user.CustomerDto;

import java.time.LocalDate;
import java.util.List;
import restaurante.example.demo.persistence.model.product.ProductEntity;
import restaurante.example.demo.presentation.dto.cart.ProductWithQuantityDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String number;
    private LocalDate date;
    private CustomerDto customer;
    private String status;
    private InvoiceDto invoice;
    private OrderStatusDto orderStatus;
    private List<Long> items;
    private List<ProductEntity> products;
    private List<ProductWithQuantityDto> productsWithQuantities;
    private String total;
}
