package restaurante.example.demo.service.interfaces.user;

import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.interfaces.common.IUseCases;

public interface IUserService  extends IUseCases<UserDto,Long> {
}
