package restaurante.example.demo.service.mapper.product;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.product.ProductEntity;
import restaurante.example.demo.presentation.dto.product.ProductDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IProductMapper  extends ISourceTargetMapper<ProductEntity, ProductDto> {
}
