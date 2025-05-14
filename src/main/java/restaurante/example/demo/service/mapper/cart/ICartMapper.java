package restaurante.example.demo.service.mapper.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.cart.CartEntity;
import restaurante.example.demo.presentation.dto.cart.CartDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;
import restaurante.example.demo.service.mapper.role.IRoleMapper;


@Mapper(componentModel = "spring", uses = IRoleMapper.class)
public interface ICartMapper extends ISourceTargetMapper<CartEntity,CartDto> {    
    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleEntitiesToNames")  
    CartDto entityToDto(CartEntity entity);

    @Override
    @Mapping(source = "customer.user.roles", target = "customer.user.roles", qualifiedByName = "mapRoleNamesToEntities")  
    CartEntity dtoToEntity(CartDto dto);   
}
