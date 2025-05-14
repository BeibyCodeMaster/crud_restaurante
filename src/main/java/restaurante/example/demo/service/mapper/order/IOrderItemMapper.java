package restaurante.example.demo.service.mapper.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.order.OrderItemEntity;
import restaurante.example.demo.presentation.dto.order.OrderItemDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

@Mapper(componentModel = "spring", uses = IRoleMapper.class)
public interface IOrderItemMapper extends ISourceTargetMapper<OrderItemEntity, OrderItemDto> {
    @Override
    @Mapping(source = "order.customer.user.roles", target = "order.customer.user.roles", qualifiedByName = "mapRoleEntitiesToNames")  
    OrderItemDto entityToDto(OrderItemEntity entity);

    @Override
    @Mapping(source = "order.customer.user.roles", target = "order.customer.user.roles", qualifiedByName = "mapRoleNamesToEntities")  
    OrderItemEntity dtoToEntity(OrderItemDto dto);
}
