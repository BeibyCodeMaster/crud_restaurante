package restaurante.example.demo.service.mapper.role;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.persistence.model.role.RoleEntity;

// Indica que esta es una interfaz de mapeo, gestionada por el contenedor de Spring con la anotación @Mapper.
// El atributo "componentModel = 'spring'" permite que Spring gestione la interfaz como un componente, facilitando
// la inyección automática donde se necesite.
@Mapper(componentModel = "spring")
public interface IRoleMapper  {

    // Método predeterminado que convierte un conjunto de RoleEntity en un conjunto de nombres de roles en formato String.
    // Este método se puede usar para mapear entidades de roles a sus nombres (por ejemplo, en un DTO).
    @Named("mapRoleEntitiesToNames")
    default Set<String> mapRoleEntitiesToNames(Set<RoleEntity> roles) {
        return roles.stream() // Convierte el conjunto de roles en un stream para aplicar operaciones de transformación.
                .map(role -> role.getName().name()) // Mapea cada RoleEntity a su nombre como String llamando a .name() en el enum.
                .collect(Collectors.toSet()); // Recolecta el resultado en un Set<String> para eliminar duplicados y mantener la colección.
    }

    // Método predeterminado que convierte un conjunto de nombres de roles (String) en un conjunto de RoleEntity.
    // Este método es útil para mapear nombres de roles desde un DTO a entidades RoleEntity en la base de datos.
    @Named("mapRoleNamesToEntities")
    @Mapping(source = "permissionList", target = "role.permissionList")
    default Set<RoleEntity> mapRoleNamesToEntities(Set<String> roleNames) {
        return roleNames.stream() // Convierte el conjunto de nombres en un stream.
                .map(roleName -> RoleEntity.builder() // Crea un nuevo RoleEntity usando el patrón de construcción (builder).
                        .name(NameRoleEnum.valueOf(roleName)) // Asigna el nombre al RoleEntity convirtiendo el String a NameRoleEnum.
                        .build()) // Finaliza la construcción del RoleEntity.
                .collect(Collectors.toSet()); // Recolecta el resultado en un Set<RoleEntity>.
    }
         
}

