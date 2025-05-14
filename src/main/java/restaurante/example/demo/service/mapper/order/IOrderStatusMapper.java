package restaurante.example.demo.service.mapper.order;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.order.OrderStatusEntity;
import restaurante.example.demo.presentation.dto.order.OrderStatusDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IOrderStatusMapper extends ISourceTargetMapper<OrderStatusEntity, OrderStatusDto> {
}
