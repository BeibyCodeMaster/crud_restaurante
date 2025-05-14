package restaurante.example.demo.presentation.dto.cart;

import ch.qos.logback.core.net.server.Client;
import java.math.BigDecimal;
import restaurante.example.demo.presentation.dto.user.CustomerDto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.model.product.ProductEntity;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private CustomerDto customer;
    private List<Long> items;
    private List<ProductEntity> products;
    private List<ProductWithQuantityDto> productsWithQuantities;
    private String vat;
    private String total;
}
