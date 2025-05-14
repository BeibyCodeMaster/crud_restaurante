package restaurante.example.demo.service.implementation.user;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurante.example.demo.exception.EntityDataAccesException;
import restaurante.example.demo.exception.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.user.ISuperDao;
import restaurante.example.demo.persistence.dao.interfaces.user.IUserDao;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.SuperEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.StatusEnum;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.persistence.model.role.PermissionEntity;
import restaurante.example.demo.presentation.dto.user.SuperDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.interfaces.user.ISuperService;
import restaurante.example.demo.service.mapper.user.ISuperMapper;
import restaurante.example.demo.service.mapper.user.IUserMapper;

@Service
// Implementación del servicio que maneja la lógica de negocio para la entidad Super.
public class SuperServiceImpl implements ISuperService {

    @Autowired
    private IUserDao userDao;  // DAO para acceder a la base de datos para la entidad Usuario.
    @Autowired
    private IUserMapper userMapper;  // Mapper para convertir entre entidad Usuario y DTO.
    @Autowired
    private ISuperDao superDo;  // DAO para acceder a la base de datos para la entidad Super.
    @Autowired
    private ISuperMapper superMapper;  // Mapper para convertir entre entidad Super y DTO.
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    // Método que obtiene todos los super usuarios y los convierte en DTOs.
    public List<SuperDto> getAll() {
        return this.superDo.findAll().stream()
                .map(this.superMapper::entityToDto)  // Convierte cada entidad de super a DTO.
                .toList();
    }

    @Override
    // Método que obtiene un super usuario por su ID y lo convierte en un DTO.
    public SuperDto getOneById(Long id) throws EntityNotFoundException {
        return this.superDo.findById(id)
                .map(this.superMapper::entityToDto)  // Convierte la entidad de super a DTO si se encuentra.
                .orElseThrow(() -> new EntityNotFoundException("El super con el id " + id + " no existe."));  // Lanza una excepción si no se encuentra el super usuario.
    }

    @Override
    // Método que crea un nuevo super usuario en la base de datos a partir de un DTO proporcionado.
    public SuperDto create(SuperDto entityDto) throws DataAccessException {
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
        // Asignar el rol de SUPER al nuevo usuario.
         Set<RoleEntity>  rolesEntity =  entityDto.getUser().getRoles().isEmpty()
                            ? Set.of( RoleEntity.builder()
                                .name(NameRoleEnum.Super)
                                .permissionList(Set.of(createPermission,redPermission,deletePermission))
                                .build())
                            : entityDto.getUser().getRoles().stream()
                                .map(role -> RoleEntity.builder()
                                                .name(NameRoleEnum.valueOf(role))
                                .build())
                                .collect(Collectors.toSet());      
        // Configurar la información de activación: fecha de inicio y estado.
        DateTimeActive dateTimeActive = DateTimeActive.builder()
                .state(StatusEnum.ACTIVE)
                .startDate(LocalDateTime.now())  // Se establece la fecha de inicio como la fecha actual.
                .build();
        // Convertir el DTO de super usuario a un DTO de usuario.
        UserDto userDto = this.superMapper.superDtoToUserDto(entityDto);
        // Convertir el DTO de usuario a una entidad para la persistencia.
        UserEntity userEntity = this.userMapper.dtoToEntity(userDto);
        userEntity.setRoles(rolesEntity);  // Asigna el rol al usuario.
        userEntity.setCreatedAt(LocalDateTime.now());  // Establece la fecha de creación del usuario.
        userEntity.setAccountNoExpired(true);
        userEntity.setAccountNoLocked(true);
        userEntity.setCredentialNoExpired(true);  
        // Crear la entidad de super usuario con los datos de usuario y activación.
        SuperEntity superEntity = SuperEntity.builder()
                .user(userEntity)
                .dateTimeActive(dateTimeActive)
                .build();
        try {
            // Guardar la entidad de super usuario en la base de datos.
            this.superDo.save(superEntity);
            // Convertir la entidad guardada en un DTO y retornarlo.
            return this.superMapper.entityToDto(superEntity);
        } catch (DataAccessException e) {
            System.out.println("service/create :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Método que actualiza un super usuario existente, validando su existencia antes.
    public SuperDto update(Long id, SuperDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        // Validar la existencia del super usuario con el ID proporcionado.
        SuperEntity superEntity = this.superDo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El super con el ID " + id + " no existe."));

        // Obtener el ID de usuario asociado al super usuario.
        Long userId = superEntity.getUser().getId();

        // Validar la existencia del usuario correspondiente.
        UserEntity userEntity = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el ID " + userId + " no existe."));

        // Actualizar los datos de la entidad de usuario con los datos del DTO proporcionado.
        this.updateEntityFromDto(userEntity, entityDto);

        try {
            // Guardar la entidad de usuario actualizada en la base de datos.
            this.userDao.save(userEntity);
            // Convertir la entidad de usuario actualizada en un DTO y retornarlo.
            return this.userMapper.userEntityToSuperDto(userEntity);
        } catch (DataAccessException e) {
            System.out.println("service/update :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Método que elimina un super usuario específico por su ID, validando su existencia primero.
    public String delete(Long id) throws EntityNotFoundException {
        this.superDo.findById(id).orElseThrow(() -> new EntityNotFoundException("El super con el ID " + id + " no existe."));
        this.superDo.delete(id);  // Elimina al super usuario de la base de datos.
        return "El super con el ID " + id + " fue eliminada.";
    }

    // Método encargado de actualizar los datos de la entidad de usuario con los datos de un DTO de super usuario.
    private void updateEntityFromDto(UserEntity userEntity, SuperDto entityDto) {
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
