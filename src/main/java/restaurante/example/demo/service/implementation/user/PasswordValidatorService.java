package restaurante.example.demo.service.implementation.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidatorService {
    
    private final PasswordEncoder passwordEncoder;

    public PasswordValidatorService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
  
    // Método para validar la contraseña
    public boolean validatePassword(String passwordUser, String passwordBd) {
        return passwordEncoder.matches(passwordUser, passwordBd);
    }
    
}
