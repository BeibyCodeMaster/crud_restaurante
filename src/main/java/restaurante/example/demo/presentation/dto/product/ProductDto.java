package restaurante.example.demo.presentation.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.ProductStatusEnum;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank(message = "Ingrese el nombre del producto.")
    private String name;
    @NotBlank(message = "Ingrese el descricion del producto.")
    private String description;
    private byte[] image;
    @NotBlank(message = "Ingrese la ruta de la imagen.")
    private String imagePath; // Solo enviamos la ruta, no el LONGBLOB
    @NotNull(message = "Ingrese el estado del producto.")
    private ProductStatusEnum status; //DISPONIBLE, NO_DISPONIBLE
    @NotNull(message = "Ingrese el precio del producto.")
    private BigDecimal price; // Precio estandar del producto
    @NotNull(message = "Ingrese la categoria del producto.")
    private CategoryDto category; // Solo enviamos el ID de la categoría
}
