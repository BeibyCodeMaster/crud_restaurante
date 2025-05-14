package restaurante.example.demo.service.mapper.order;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.order.InvoiceEntity;
import restaurante.example.demo.presentation.dto.order.InvoiceDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IInvoiceMapper extends ISourceTargetMapper<InvoiceEntity, InvoiceDto> {
}
