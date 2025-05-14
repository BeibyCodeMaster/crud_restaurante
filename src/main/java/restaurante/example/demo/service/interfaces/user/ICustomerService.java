package restaurante.example.demo.service.interfaces.user;

import restaurante.example.demo.presentation.dto.user.CustomerDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface ICustomerService extends IUseCases<CustomerDto,Long> {
}
