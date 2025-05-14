package restaurante.example.demo.presentation.dto.role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.persistence.model.role.PermissionEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long roleId;
    private NameRoleEnum name;
    private Set<PermissionEntity> permissionList;
}
