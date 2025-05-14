package restaurante.example.demo.service.implementation.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.config.security.jwt.JwtUtils;
import restaurante.example.demo.exception.AutBadCredentialsException;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.repositoy.user.IUserRepository;
import restaurante.example.demo.presentation.dto.auth.AuthLoginRequest;
import restaurante.example.demo.presentation.dto.auth.AuthResponse;


/**
 * Implementación del servicio UserDetailsService que integra la gestión de usuarios
 * con Spring Security. Este servicio incluye la autenticación de usuarios y la 
 * generación de tokens JWT.
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils; // Utilidad para manejar tokens JWT

    @Autowired
    private IUserRepository userRepository; // Repositorio para acceder a los datos de usuario

    @Autowired
    private PasswordValidatorService passwordValidatorService; // Servicio para validar contraseñas

    private static final boolean ACCOUNT_ENABLED  = true; // Indicador de si las cuentas están habilitadas

    private final boolean STATUS_OK = true;
    
    /**
     * Carga los detalles de un usuario mediante su nombre de usuario.
     *
     * @param username el nombre de usuario.
     * @return los detalles del usuario en un objeto UserDetails.
     * @throws UsernameNotFoundException si no se encuentra el usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Obtener el usuario de la base de datos
        UserEntity user = this.findUserByUsername(username);

        // Obtener roles y permisos asociados al usuario
        Collection<? extends GrantedAuthority> authorities = this.mapRolesAndPermissions(user);

        // Retornar un usuario compatible con Spring Security
        return new User(
                user.getUserName(),
                user.getPassword(),
                ACCOUNT_ENABLED,
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorities
        );
    }
   
    /**
     * Autentica un usuario basado en sus credenciales y genera un token JWT si las credenciales son válidas.
     *
     * @param loginRequest el DTO con las credenciales de autenticación.
     * @return un objeto AuthResponse con el token generado.
     * @throws AutBadCredentialsException si las credenciales son inválidas.
     */
    public AuthResponse authenticateUser(AuthLoginRequest loginRequest) throws AutBadCredentialsException {
        String userName = loginRequest.userName();
        String password = loginRequest.password();

        // Autenticar al usuario
        Authentication authentication = this.authenticate(userName, password);

        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String accessToken = this.jwtUtils.createToken(authentication);

        // Retornar la respuesta
        return new AuthResponse(
                userName, 
                "Usuario autenticado exitosamente", 
                accessToken, 
                true
        );
    }

    /**
     * Realiza la autenticación de un usuario validando sus credenciales.
     *
     * @param userName el nombre de usuario.
     * @param password la contraseña.
     * @return un objeto Authentication válido.
     * @throws AutBadCredentialsException si las credenciales son incorrectas.
     */
    private Authentication authenticate(String userName, String password) throws AutBadCredentialsException {
        try {
            // Cargar detalles del usuario
            UserDetails userDetails = this.loadUserByUsername(userName);

            // Validar la contraseña
            if (!this.passwordValidatorService.validatePassword(password, userDetails.getPassword())) {
                throw new AutBadCredentialsException("Contraseña inválida.");
            }

            // Crear y retornar el token de autenticación
            return new UsernamePasswordAuthenticationToken(
                    userName,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );

        } catch (UsernameNotFoundException e) {
            System.out.println(e.getMessage());
            throw new AutBadCredentialsException("Usuario o contraseña inválidos.");
        }
    }
    
    /**
     * Busca un usuario en el repositorio por su nombre de usuario.
     *
     * @param userName el nombre de usuario.
     * @return el usuario encontrado.
     * @throws UsernameNotFoundException si no se encuentra el usuario.
     */
    private UserEntity findUserByUsername(String userName) throws UsernameNotFoundException {
        return this.userRepository.getUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + userName + " no existe."));
    }

    /**
     * Genera una lista de roles y permisos del usuario en formato GrantedAuthority.
     * @param userEntity El usuario del cual obtener roles y permisos.
     * @return Una lista de autoridades (roles y permisos).
     */
    private List<SimpleGrantedAuthority> mapRolesAndPermissions(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Agregar roles con prefijo "ROLE_"
        userEntity.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()))
        );

        // Agregar permisos
        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> 
                    authorities.add(new SimpleGrantedAuthority(permission.getName()))
                );

        return authorities;
    } 


    public AuthResponse createTokenForRegisteredUser(String userName){

        UserEntity userEntity = this.findUserByUsername(userName);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> {
             authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())));
        });

        userEntity.getRoles().stream()
                .flatMap( role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));



        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUserName(),
                                                                                userEntity.getPassword(),
                                                                                authorityList);

        String accesToken = this.jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(userEntity.getUserName(),
                                            "El usuario se registro correctamente.",
                                                      accesToken,
                                                       STATUS_OK);
        return authResponse;
    }
}
