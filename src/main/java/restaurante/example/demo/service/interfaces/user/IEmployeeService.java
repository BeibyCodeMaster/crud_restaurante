package restaurante.example.demo.service.interfaces.user;

import restaurante.example.demo.presentation.dto.user.EmployeeDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IEmployeeService extends IUseCases<EmployeeDto,Long> {
}
