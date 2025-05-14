package restaurante.example.demo.service.interfaces.order;

import restaurante.example.demo.presentation.dto.order.InvoiceDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IInvoiceService extends IUseCases<InvoiceDto,Long> {
}
