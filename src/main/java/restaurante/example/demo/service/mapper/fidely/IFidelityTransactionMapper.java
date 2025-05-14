package restaurante.example.demo.service.mapper.fidely;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.fidelity.FidelityTransactionEntity;
import restaurante.example.demo.presentation.dto.fidelity.FidelityTransactionDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

@Mapper(componentModel = "spring", uses = IRoleMapper.class)
public interface IFidelityTransactionMapper extends ISourceTargetMapper<FidelityTransactionEntity, FidelityTransactionDto> {    
    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleEntitiesToNames")  
    FidelityTransactionDto entityToDto(FidelityTransactionEntity entity);

    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleNamesToEntities")  
    FidelityTransactionEntity dtoToEntity(FidelityTransactionDto dto);
}
