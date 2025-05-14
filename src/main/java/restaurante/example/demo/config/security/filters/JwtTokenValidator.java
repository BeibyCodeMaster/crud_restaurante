package restaurante.example.demo.config.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import restaurante.example.demo.config.security.jwt.JwtUtils;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    // Constructor que recibe una instancia de JwtUtils para manejar operaciones relacionadas con JWT.
    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Método que intercepta las solicitudes HTTP y valida el token JWT si está presente.
     *
     * @param request     La solicitud HTTP entrante.
     * @param response    La respuesta HTTP saliente.
     * @param filterChain La cadena de filtros que se debe continuar después de este filtro.
     * @throws ServletException En caso de errores relacionados con el servlet.
     * @throws IOException      En caso de errores de entrada/salida.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Obtiene el encabezado de autorización de la solicitud.
        String jwtTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verifica si el encabezado no es nulo y comienza con "Bearer".
        if (jwtTokenHeader != null && jwtTokenHeader.startsWith("Bearer")) {
            // Extrae el token JWT eliminando la palabra "Bearer " (7 caracteres).
            String token = jwtTokenHeader.substring(7);

            // Valida el token utilizando JwtUtils y obtiene el objeto DecodedJWT si es válido.
            DecodedJWT tokenJwtValid = this.jwtUtils.validateToken(token);

            if (tokenJwtValid != null) {
                // Extrae el nombre de usuario del token JWT.
                String userName = this.jwtUtils.extractUserNameFromToken(tokenJwtValid);

                // Obtiene las autoridades (roles) del usuario como una cadena separada por comas.
                String authoritiesToString = this.jwtUtils.getSpecificClaim(tokenJwtValid, "authorities").asString();

                // Convierte la cadena de roles en una colección de GrantedAuthority.
                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesToString);

                // Obtiene el contexto de seguridad actual.
                SecurityContext context = SecurityContextHolder.getContext();

                // Crea un objeto de autenticación con el nombre de usuario y las autoridades.
                Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);

                // Establece el objeto de autenticación en el contexto de seguridad.
                context.setAuthentication(authentication);

                // Configura el contexto de seguridad en SecurityContextHolder.
                SecurityContextHolder.setContext(context);
            }
        }

        // Continúa la cadena de filtros después de este filtro.
        filterChain.doFilter(request, response);
    }
}
