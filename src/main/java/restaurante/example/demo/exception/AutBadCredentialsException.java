package restaurante.example.demo.exception;

import org.springframework.security.authentication.BadCredentialsException;


public class AutBadCredentialsException extends BadCredentialsException {
    
    public AutBadCredentialsException(String message) {
        super(message);
    }
    
}
