package restaurante.example.demo.service.mapper.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.booking.ReservationEntity;
import restaurante.example.demo.presentation.dto.booking.ReservationDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

@Mapper(componentModel = "spring", uses = IRoleMapper.class)
public interface IReservationMapper extends ISourceTargetMapper<ReservationEntity, ReservationDto>{
    
    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleEntitiesToNames")  
    ReservationDto entityToDto(ReservationEntity entity);

    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleNamesToEntities")  
    ReservationEntity dtoToEntity(ReservationDto dto);       
    
}
