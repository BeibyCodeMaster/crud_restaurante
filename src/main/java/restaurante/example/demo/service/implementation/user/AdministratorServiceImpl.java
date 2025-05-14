package restaurante.example.demo.service.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.user.IAdministratorDao;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.AdministratorEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.StatusEnum;
import restaurante.example.demo.presentation.dto.user.AdministratorDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.interfaces.user.IAdministratorService;
import restaurante.example.demo.service.mapper.user.IAdministratorMapper;
import restaurante.example.demo.service.mapper.user.IUserMapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import restaurante.example.demo.persistence.dao.interfaces.user.IUserDao;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.persistence.model.role.PermissionEntity;

@Service
// Servicio encargado de implementar la lógica de negocio para la entidad Administrador.
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private IUserDao userDao; // DAO para acceder a la base de datos para la entidad Usuario
    @Autowired
    private IAdministratorDao administratorDao; // DAO para acceder a la base de datos para la entidad Administrador
    @Autowired
    private IAdministratorMapper administratorMapper; // Mapper para convertir entre entidad Administrador y DTO
    @Autowired
    private IUserMapper userMapper; // Mapper para convertir entre entidad Usuario y DTO
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    // Obtiene la lista de todos los administradores en forma de DTOs.
    public List<AdministratorDto> getAll() {
        return this.administratorDao.findAll().stream()
                .map(this.administratorMapper::entityToDto) // Convierte cada entidad a DTO
                .toList(); // Recolecta los DTOs en una lista
    }

    @Override
    // Obtiene un administrador específico por su ID y lo convierte en un DTO.
    public AdministratorDto getOneById(Long id) throws EntityNotFoundException {
        return this.administratorDao.findById(id)
                .map(this.administratorMapper::entityToDto) // Convierte la entidad a DTO
                .orElseThrow(() -> new EntityNotFoundException("El administrador con el ID " + id + " no existe."));
    }

    @Override
    // Crea un nuevo administrador en la base de datos a partir de un DTO proporcionado.
    public AdministratorDto create(AdministratorDto entityDto) throws EntityDataAccesException {
        // encriptamos la clave
        String password = entityDto.getUser().getPassword();
        entityDto.getUser().setPassword(this.passwordEncoder.encode(password));
        // creamos los permisos        
        PermissionEntity createPermission = PermissionEntity.builder()
                  .name("CREATE")
                  .build();        
        PermissionEntity redPermission = PermissionEntity.builder()
                .name("READ")
                .build();        
        PermissionEntity deletePermission = PermissionEntity.builder()
                .name("DELETE")
                .build();

        // Asignar el rol de Administrador al nuevo usuario.
        Set<RoleEntity>  rolesEntity =  entityDto.getUser().getRoles().isEmpty()
                            ? Set.of( RoleEntity.builder()
                                .name(NameRoleEnum.Administrador)
                                .permissionList(Set.of(createPermission,redPermission,deletePermission))
                                .build())
                            : entityDto.getUser().getRoles().stream()
                                .map(role -> RoleEntity.builder()
                                                .name(NameRoleEnum.valueOf(role))
                                .build())
                                .collect(Collectors.toSet());
        // Configura la información de activación del usuario (fecha de inicio y estado).
        DateTimeActive dateTimeActive = DateTimeActive.builder()
                .state(StatusEnum.ACTIVE) // El estado es activo
                .startDate(LocalDateTime.now()) // Fecha de inicio es la fecha actual
                .build();
        // Convierte el DTO de administrador a DTO de usuario.
        UserDto userDto = this.administratorMapper.adminDtoToUserDto(entityDto);
        // Convierte el DTO de usuario a entidad de usuario.
        UserEntity userEntity = this.userMapper.dtoToEntity(userDto);
        // Asocia el rol al usuario y establece la fecha de creación.
        userEntity.setRoles(rolesEntity); // El rol de ADMINISTRATOR se asigna al usuario
        userEntity.setCreatedAt(LocalDateTime.now()); // Fecha de creación es la fecha actual
        userEntity.setAccountNoExpired(true);
        userEntity.setAccountNoLocked(true);
        userEntity.setCredentialNoExpired(true);  
        // Crea la entidad de administrador con los datos de usuario y activación.
        AdministratorEntity administratorEntity = AdministratorEntity.builder()
                .user(userEntity) // Asocia el usuario al administrador
                .dateTimeActive(dateTimeActive) // Asocia la información de activación
                .build();
        // Guarda la entidad de administrador en la base de datos.
        try {
            this.administratorDao.save(administratorEntity); // Persistencia de datos
            // Convierte la entidad guardada a DTO y la retorna.
            return this.administratorMapper.entityToDto(administratorEntity);
        } catch (DataAccessException e) {
            // Maneja los errores de acceso a datos y lanza una excepción personalizada.
            System.out.println("service/create :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Actualiza un administrador existente, validando primero su existencia.
    public AdministratorDto update(Long id, AdministratorDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        // Valida si el administrador con el ID proporcionado existe.
        AdministratorEntity administratorEntity = this.administratorDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El administrador con el ID " + id + " no existe."));

        // Obtiene el ID de usuario asociado al administrador.
        Long userId = administratorEntity.getUser().getId();

        // Valida si el usuario correspondiente existe.
        UserEntity userEntity = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el ID " + userId + " no existe."));

        // Se actualiza la entidad de usuario con los datos del DTO proporcionado.
        this.updateEntityFromDto(userEntity, entityDto);

        try {
            // Guarda la entidad de usuario actualizada en la base de datos.
            this.userDao.save(userEntity);
            // Convierte la entidad de usuario actualizada a un DTO de administrador y lo retorna.
            return this.userMapper.userEntityToAdminstratorDto(userEntity);
        } catch (DataAccessException e) {
            // Maneja los errores de acceso a datos y lanza una excepción personalizada.
            System.out.println("service/update :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Elimina un administrador específico por su ID, verificando su existencia.
    public String delete(Long id) throws EntityNotFoundException {
        // Valida si el administrador con el ID proporcionado existe.
        this.administratorDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El administrador con el ID " + id + " no existe."));

        // Elimina la entidad de administrador de la base de datos.
        this.administratorDao.delete(id);
        return "El administrador con el ID " + id + " fue eliminado.";
    }

    // Método encargado de actualizar los datos de la entidad Usuario con los del DTO proporcionado.
    private void updateEntityFromDto(UserEntity userEntity, AdministratorDto entityDto) {
        userEntity.setFirstName(entityDto.getUser().getFirstName()); // Actualiza el nombre
        userEntity.setLastName(entityDto.getUser().getLastName()); // Actualiza el apellido
        userEntity.setEmail(entityDto.getUser().getEmail()); // Actualiza el correo electrónico
        userEntity.setAddress(entityDto.getUser().getAddress()); // Actualiza la dirección
        userEntity.setPhone(entityDto.getUser().getPhone()); // Actualiza el teléfono
        userEntity.setPassword(entityDto.getUser().getPassword()); // Actualiza la contraseña
        userEntity.setUserName(entityDto.getUser().getUserName()); // Actualiza el nombre de usuario
        userEntity.setBirthDate(entityDto.getUser().getBirthDate()); // Actualiza la fecha de nacimiento
        userEntity.setUpdatedAt(LocalDateTime.now()); // Actualiza la fecha de modificación
    }
}