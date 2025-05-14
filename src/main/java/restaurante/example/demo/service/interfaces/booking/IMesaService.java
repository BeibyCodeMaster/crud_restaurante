package restaurante.example.demo.service.interfaces.booking;


import restaurante.example.demo.presentation.dto.booking.MesaDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

// Interfaz que define los casos de uso específicos para la entidad Mesa en el sistema.
// Extiende la interfaz genérica IUseCases.
public interface IMesaService extends IUseCases<MesaDto, Long> {
    // Aquí puedes definir métodos adicionales exclusivos para los casos de uso de Mesa.
}

