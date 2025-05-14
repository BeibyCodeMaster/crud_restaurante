package restaurante.example.demo.service.mapper.user;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.user.AdministratorEntity;
import restaurante.example.demo.presentation.dto.user.AdministratorDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.mapper.role.IRoleMapper;

@Mapper(componentModel = "spring", uses = IRoleMapper.class)
// Interfaz que define la conversión entre objetos DTO y entidades de administradores.
// Utiliza MapStruct para la generación automática de código de mapeo.
public interface IAdministratorMapper {
    @Mapping(source = "dateTimeActive.startDate", target = "startDate")
    @Mapping(source = "dateTimeActive.endDate", target = "endDate")
    @Mapping(source = "dateTimeActive.state", target = "state")
    @Mapping(source = "user.roles", target = "user.roles" , qualifiedByName = "mapRoleEntitiesToNames")
    AdministratorDto entityToDto(AdministratorEntity entity); // Mapea una entidad Administrator a DTO.

    @Mapping(source = "startDate", target = "dateTimeActive.startDate")
    @Mapping(source = "endDate", target = "dateTimeActive.endDate")
    @Mapping(source = "state", target = "dateTimeActive.state")
    @Mapping(source = "user.roles", target = "user.roles" , qualifiedByName = "mapRoleNamesToEntities")
    AdministratorEntity dtoToEntity(AdministratorDto dto); // Mapea un DTO de Administrator a entidad.

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.address", target = "address")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.birthDate", target = "birthDate")
    @Mapping(source = "user.roles", target = "roles")
    @Mapping(source = "user.password", target = "password")
    @Mapping(source = "user.createdAt", target = "createdAt")
    @Mapping(source = "user.updatedAt", target = "updatedAt")
    UserDto adminDtoToUserDto(AdministratorDto dto); // Mapea un DTO de administrador a un DTO de usuario.
}