package restaurante.example.demo.service.mapper.booking;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.booking.ReservationStatusEntity;
import restaurante.example.demo.presentation.dto.booking.ReservationStatusDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IReservationStatusMapper extends ISourceTargetMapper<ReservationStatusEntity, ReservationStatusDto> {
}
