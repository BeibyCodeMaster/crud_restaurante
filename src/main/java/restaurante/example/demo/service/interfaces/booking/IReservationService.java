package restaurante.example.demo.service.interfaces.booking;

import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.presentation.dto.booking.ReservationDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IReservationService extends IUseCases<ReservationDto,Long> {
    ReservationDto cancel(Long id) throws EntityNotFoundException; 
}
