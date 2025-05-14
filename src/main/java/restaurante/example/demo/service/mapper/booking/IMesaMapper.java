package restaurante.example.demo.service.mapper.booking;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.booking.MesaEntity;
import restaurante.example.demo.presentation.dto.booking.MesaDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

// Interfaz para realizar la conversión entre objetos DTO y entidades de Mesa.
// Utiliza MapStruct para la generación automática de código de mapeo.
@Mapper(componentModel = "spring")
public interface IMesaMapper extends ISourceTargetMapper<MesaEntity, MesaDto>{    
  
}
