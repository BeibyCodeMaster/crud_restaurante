package restaurante.example.demo.service.mapper.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.order.OrderEntity;
import restaurante.example.demo.presentation.dto.order.OrderDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

@Mapper(componentModel = "spring", uses = IRoleMapper.class)
public interface IOrderMapper extends ISourceTargetMapper<OrderEntity, OrderDto> {
    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleEntitiesToNames")  
    OrderDto entityToDto(OrderEntity entity);

    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleNamesToEntities")  
    OrderEntity dtoToEntity(OrderDto dto);
}
