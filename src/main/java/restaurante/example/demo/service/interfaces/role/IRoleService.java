package restaurante.example.demo.service.interfaces.role;

import restaurante.example.demo.presentation.dto.role.RoleDto;

import java.util.List;

public interface IRoleService {
   List<RoleDto> getAllRoles();
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(RoleDto roleDto);
    String deleteRole();
}
