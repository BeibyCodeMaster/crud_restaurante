package restaurante.example.demo.service.interfaces.user;

import restaurante.example.demo.presentation.dto.user.AdministratorDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;
// Interfaz que define los casos de uso específicos para la entidad de administrador en el sistema.
// Extiende la interfaz genérica IUseCases.
public interface IAdministratorService extends IUseCases<AdministratorDto, Long> {
    // Métodos adicionales exclusivos para los casos de uso de un administrador.
}