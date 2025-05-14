package restaurante.example.demo.service.mapper.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.cart.CartItemEntity;
import restaurante.example.demo.presentation.dto.cart.CartItemDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

@Mapper(componentModel = "spring", uses = IRoleMapper.class)
public interface ICartItem extends ISourceTargetMapper<CartItemEntity, CartItemDto> {
    
    @Override
    @Mapping(source = "cart.customer.user.roles", target = "cart.customer.user.roles", qualifiedByName = "mapRoleEntitiesToNames")  
    CartItemDto entityToDto(CartItemEntity entity);

    @Override
    @Mapping(source = "cart.customer.user.roles", target = "cart.customer.user.roles", qualifiedByName = "mapRoleNamesToEntities")  
    CartItemEntity dtoToEntity(CartItemDto dto);  
    
}
