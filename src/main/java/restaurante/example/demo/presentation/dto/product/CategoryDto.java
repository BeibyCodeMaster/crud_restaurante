package restaurante.example.demo.presentation.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;    
    @NotBlank(message = "Ingrese el nombre.")
    private String name;
    @NotBlank(message = "Ingrese la descripcion.")
    private String description;
    private byte[] image;
    @NotBlank(message = "Ingrese la ruta de la imagen.")
    private String imagePath; // Solo enviamos la ruta, no el LONGBLOB
}
