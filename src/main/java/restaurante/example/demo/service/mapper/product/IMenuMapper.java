package restaurante.example.demo.service.mapper.product;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.product.MenuEntity;
import restaurante.example.demo.presentation.dto.product.MenuDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IMenuMapper  extends ISourceTargetMapper<MenuEntity, MenuDto> {

    @Override
    public MenuEntity dtoToEntity(MenuDto dto);

    @Override
    public MenuDto entityToDto(MenuEntity entity);
        
}
