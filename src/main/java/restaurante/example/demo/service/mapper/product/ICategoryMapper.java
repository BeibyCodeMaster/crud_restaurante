package restaurante.example.demo.service.mapper.product;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.product.CategoryEntity;
import restaurante.example.demo.presentation.dto.product.CategoryDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapper extends ISourceTargetMapper<CategoryEntity, CategoryDto> {
}
