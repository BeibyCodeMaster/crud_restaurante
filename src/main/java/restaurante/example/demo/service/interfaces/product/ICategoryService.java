package restaurante.example.demo.service.interfaces.product;

import restaurante.example.demo.persistence.model.product.CategoryEntity;
import restaurante.example.demo.presentation.dto.product.CategoryDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface ICategoryService extends IUseCases<CategoryDto,Long> {
}
