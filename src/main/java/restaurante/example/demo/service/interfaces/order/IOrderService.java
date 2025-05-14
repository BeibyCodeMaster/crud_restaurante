package restaurante.example.demo.service.interfaces.order;

import restaurante.example.demo.presentation.dto.order.OrderDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IOrderService extends IUseCases<OrderDto,Long> {
}
