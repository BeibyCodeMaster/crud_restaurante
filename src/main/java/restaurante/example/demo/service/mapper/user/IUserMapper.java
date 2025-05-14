package restaurante.example.demo.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.presentation.dto.user.*;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

// Interfaz que define el mapeo entre las entidades UserEntity y sus respectivos DTOs.
// Utiliza MapStruct para la generación automática de código de mapeo.
// Los DTOs incluyen diferentes tipos de roles de usuario: Administrator, Employee, Super, y Customer.
@Mapper(componentModel = "spring" , uses = IRoleMapper.class)
public interface IUserMapper {

    // Mapea una entidad UserEntity a un DTO para un administrador (AdministratorDto).
    // Cada atributo de UserEntity se mapea al correspondiente atributo en AdministratorDto.
    @Mapping(source = "id", target = "user.id")  // Mapea 'userId' de UserEntity a 'userId' en el DTO.
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "address", target = "user.address")
    @Mapping(source = "phone", target = "user.phone")
    @Mapping(source = "userName", target = "user.userName")
    @Mapping(source = "birthDate", target = "user.birthDate")
    @Mapping(source = "roles", target = "user.roles", qualifiedByName = "mapRoleEntitiesToNames")  // Lista de roles del usuario.
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "createdAt", target = "user.createdAt")
    @Mapping(source = "updatedAt", target = "user.updatedAt")
    AdministratorDto userEntityToAdminstratorDto(UserEntity entity);

    // Mapea una entidad UserEntity a un DTO para un empleado (EmployeeDto).
    @Mapping(source = "id", target = "user.id")  // Mapea 'userId' de UserEntity a 'userId' en el DTO.
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "address", target = "user.address")
    @Mapping(source = "phone", target = "user.phone")
    @Mapping(source = "userName", target = "user.userName")
    @Mapping(source = "birthDate", target = "user.birthDate")
    @Mapping(source = "roles", target = "user.roles", qualifiedByName = "mapRoleEntitiesToNames")  // Lista de roles del usuario.
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "createdAt", target = "user.createdAt")
    @Mapping(source = "updatedAt", target = "user.updatedAt")
    EmployeeDto userEntityToEmployeeDto(UserEntity entity);

    // Mapea una entidad UserEntity a un DTO para un superusuario (SuperDto).
    @Mapping(source = "id", target = "user.id")  // Mapea 'userId' de UserEntity a 'userId' en el DTO.
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "address", target = "user.address")
    @Mapping(source = "phone", target = "user.phone")
    @Mapping(source = "userName", target = "user.userName")
    @Mapping(source = "birthDate", target = "user.birthDate")
    @Mapping(source = "roles", target = "user.roles", qualifiedByName = "mapRoleEntitiesToNames")  // Lista de roles del usuario.
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "createdAt", target = "user.createdAt")
    @Mapping(source = "updatedAt", target = "user.updatedAt")
    SuperDto userEntityToSuperDto(UserEntity entity);

    // Mapea una entidad UserEntity a un DTO para un cliente (CustomerDto).
    @Mapping(source = "id", target = "user.id")  // Mapea 'userId' de UserEntity a 'userId' en el DTO.
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "address", target = "user.address")
    @Mapping(source = "phone", target = "user.phone")
    @Mapping(source = "userName", target = "user.userName")
    @Mapping(source = "birthDate", target = "user.birthDate")
    @Mapping(source = "roles", target = "user.roles", qualifiedByName = "mapRoleEntitiesToNames")  // Lista de roles del usuario.
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "createdAt", target = "user.createdAt")
    @Mapping(source = "updatedAt", target = "user.updatedAt")
    CustomerDto userEntityToCustomerDto(UserEntity entity); 

    @Mapping(source = "roles", target = "roles" , qualifiedByName = "mapRoleNamesToEntities")
    UserEntity dtoToEntity(UserDto userDto);
}