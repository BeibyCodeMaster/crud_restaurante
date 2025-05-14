package restaurante.example.demo.persistence.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import restaurante.example.demo.persistence.enums.StatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
// Clase que manipula las fechas relacionadas con el estado activo de una entidad.
public class DateTimeActive {

    @Column(name = "fecha_inicio")
    private LocalDateTime startDate; // Fecha de inicio de la actividad o del estado activo.

    @Column(name = "fecha_fin")
    private LocalDateTime endDate; // Fecha de finalización de la actividad o del estado activo.

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private StatusEnum state = StatusEnum.ACTIVE; // Estado actual de la actividad (por ejemplo, activo, inactivo). No puede ser nulo.
}