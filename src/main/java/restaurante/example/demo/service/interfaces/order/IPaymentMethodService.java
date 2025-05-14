package restaurante.example.demo.service.interfaces.order;

import restaurante.example.demo.presentation.dto.order.PaymentMethodDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IPaymentMethodService extends IUseCases<PaymentMethodDto,Long> {
}
