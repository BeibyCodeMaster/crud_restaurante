package restaurante.example.demo.service.interfaces.order;

import restaurante.example.demo.presentation.dto.order.OrderStatusDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IOrderStatusService extends IUseCases<OrderStatusDto,Long> {
}
