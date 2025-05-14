package restaurante.example.demo.presentation.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.StatusEnum;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long id;
    @NotBlank(message = "Ingrese el nombre del menu.")
    private String name;
    @NotBlank(message = "Ingrese la descripcion menu.")
    private String description;
    private byte[] image;
    @NotBlank(message = "Ingrese la ruta de la imagen.")
    private String imagePath; // Solo enviamos la ruta, no el LONGBLOB
    @NotNull(message = "Ingrese el estado del menu.")
    private boolean status; // ACTIVO, INACTIVO
    @NotNull(message = "Ingrese los productos.")
    private Set<ProductDto> products;
    private String totalPrice; // Precio total calculado de los productos

}
