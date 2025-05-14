package restaurante.example.demo.persistence.model.booking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurante.example.demo.persistence.enums.MesaLocationEnum;
import restaurante.example.demo.persistence.enums.MesaStateEnum;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="mesa")
// Clase que representa la entidad "Mesa", que corresponde a la tabla "mesa" en la base de datos.
public class MesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mesa_id")
    private Integer id; // Identificador único de la mesa

    @Column(name = "codigo", nullable = false, unique = true)
    private String code; // Código único de la mesa

    @Column(name = "capacidad", nullable = false)
    private byte capacity; // Capacidad máxima de personas que puede acomodar la mesa

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private MesaStateEnum status ; // Estado actual de la mesa (disponible, ocupada, reservada)

    @Enumerated(EnumType.STRING)
    @Column(name = "ubicacion", nullable = false)
    private MesaLocationEnum location ; // Ubicación de la mesa dentro del establecimiento (interior, exterior, terraza)

    @Column(name = "decoracion", columnDefinition = "TINYINT(1)")
    private Boolean decoration;   // Indica si la mesa está decorada

    @Column(name = "imagen", length = 50000000)
    private byte[] image; // Imagen asociada a la mesa (puede ser una foto, etc.)

    @Column(name = "ruta_imagen")
    private String imagePath; // Ruta del archivo de imagen de la mesa en el sistema

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}




