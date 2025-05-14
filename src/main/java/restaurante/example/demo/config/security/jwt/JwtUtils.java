package restaurante.example.demo.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.time.expiration}")
    private String expirationTime; // Tiempo de expiración del token en milisegundos
    @Value("${jwt.secret.key.private}")
    private String secretKey; // Clave secreta utilizada para firmar el token
    @Value("${security.jwt.user.generator}")
    private String tokenIssuer; // Emisor del token (usualmente el nombre del servicio)

    /**
     * Crea un token JWT basado en la autenticación proporcionada.
     *
     * @param authentication Información de la autenticación del usuario.
     * @return El token JWT generado.
     */
    public String createToken(Authentication authentication){
        // Extrae el nombre de usuario de la autenticación
        String username = this.extractUsername(authentication);

        // Obtiene la fecha de emisión y expiración del token
        Date issuedAt = this.getCurrentDate(false); // Fecha de emisión
        Date expirationDate = this.getCurrentDate(true); // Fecha de expiración

        // Genera un identificador único para el token
        String tokenId = this.generateTokenId();

        // Extrae las autoridades (roles) del usuario
        String authorities = this.extractAuthorities(authentication);

        // Obtiene la clave de firma para el token
        Algorithm signingKey = this.getSigningToToken();

        // Construye el token JWT utilizando la información obtenida
        String jwtToken = JWT.create()
                .withIssuer(this.tokenIssuer) // Emisor del token
                .withSubject(username) // Asunto del token (nombre de usuario)
                .withClaim("authorities", authorities) // Agrega las autoridades como un "claim"
                .withIssuedAt(issuedAt) // Fecha de emisión
                .withExpiresAt(expirationDate) // Fecha de expiración
                .withJWTId(tokenId) // Identificador del token
                .withNotBefore(this.getCurrentDate(false)) // Establece la fecha en la que el token no será válido hasta
                .sign(signingKey);// Firma el token
        return jwtToken; // Retorna el token JWT generado
    }

    /**
     * Obtiene la fecha actual, o la fecha de expiración si es necesario.
     *
     * @param isExpiration Indica si se debe calcular la fecha de expiración.
     * @return La fecha actual o de expiración.
     */
    private Date getCurrentDate(boolean isExpiration){
        long currentTimeMillis = System.currentTimeMillis(); // Obtiene el tiempo actual en milisegundos
        return new Date(isExpiration ? currentTimeMillis + Long.parseLong(expirationTime) : currentTimeMillis); // Calcula la fecha de expiración si es necesario
    }

    /**
     * Obtiene la clave de firma para el token utilizando la clave secreta.
     *
     * @return La clave de firma para el token.
     */
    private Algorithm getSigningToToken(){
        return  Algorithm.HMAC256(this.secretKey); // Crea una clave de firma HMAC
    }

    /**
     * Genera un identificador único para el token JWT.
     *
     * @return Un identificador único generado aleatoriamente.
     */
    private String generateTokenId(){
        return UUID.randomUUID().toString(); // Genera un UUID aleatorio como identificador único
    }

    /**
     * Extrae las autoridades (roles) del usuario a partir de la autenticación.
     *
     * @param authentication Información de la autenticación del usuario.
     * @return Una cadena de texto con las autoridades separadas por comas.
     */
    private String extractAuthorities(Authentication authentication){
        return authentication.getAuthorities().stream() // Obtiene las autoridades del usuario
                .map(GrantedAuthority::getAuthority) // Extrae las autoridades como cadenas
                .collect(Collectors.joining(",")); // Une las autoridades en una sola cadena separada por comas
    }

    /**
     * Extrae el nombre de usuario de la autenticación proporcionada.
     *
     * @param authentication Información de la autenticación del usuario.
     * @return El nombre de usuario extraído de la autenticación.
     */
    private String extractUsername(Authentication authentication){
        return authentication.getPrincipal().toString(); // Extrae el nombre de usuario desde el principal de la autenticación
    }

    /**
     * Valida un token JWT proporcionado y devuelve el objeto DecodedJWT si es válido.
     *
     * @param token El token JWT que se va a validar.
     * @return El objeto DecodedJWT que contiene los datos del token decodificado.
     * @throws JWTVerificationException Si el token es inválido.
     */
    public DecodedJWT validateToken(String token){
        try {
            // Obtiene la clave de firma para el token
            Algorithm signingKey = this.getSigningToToken();
            JWTVerifier jwtVerifier = JWT.require(signingKey)
                    .withIssuer(this.tokenIssuer)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT; // Devuelve el token decodificado si es válido
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalid, not Authorized"); // Lanza excepción si el token es inválido
        }
    }

    /**
     * Extrae el nombre de usuario desde el token decodificado.
     *
     * @param decodedJWT El token decodificado.
     * @return El nombre de usuario extraído del token.
     */
    public String extractUserNameFromToken(DecodedJWT decodedJWT ){
        return decodedJWT.getSubject().toString(); // Extrae el nombre de usuario del token decodificado
    }

    /**
     * Obtiene un claim específico de un token decodificado.
     *
     * @param decodedJWT El token decodificado.
     * @param claimName El nombre del claim que se quiere obtener.
     * @return El claim solicitado.
     */
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName); // Devuelve el claim específico solicitado
    }

    /**
     * Obtiene todos los claims de un token decodificado.
     *
     * @param decodedJWT El token decodificado.
     * @return Un mapa con todos los claims del token.
     */
    public Map<String,Claim> getAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims(); // Devuelve todos los claims del token
    }
}
