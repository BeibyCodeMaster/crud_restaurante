package restaurante.example.demo.service.interfaces.order;

import restaurante.example.demo.presentation.dto.order.OrderItemDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IOrderItemService extends IUseCases<OrderItemDto,Long> {
}
