package restaurante.example.demo.service.mapper.fidely;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.fidelity.FidelityEntity;
import restaurante.example.demo.presentation.dto.fidelity.FidelityDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IFidelityMapper extends ISourceTargetMapper<FidelityEntity, FidelityDto> {
}
