package restaurante.example.demo.service.mapper.order;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.order.PaymentMethodEntity;
import restaurante.example.demo.presentation.dto.order.PaymentMethodDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IPaymentMethodMapper extends ISourceTargetMapper<PaymentMethodEntity, PaymentMethodDto> {
}
