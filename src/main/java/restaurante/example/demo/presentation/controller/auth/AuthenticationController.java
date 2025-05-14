package restaurante.example.demo.presentation.controller.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurante.example.demo.exception.AutBadCredentialsException;
import restaurante.example.demo.presentation.dto.auth.AuthLoginRequest;
import restaurante.example.demo.presentation.dto.auth.AuthResponse;
import restaurante.example.demo.service.implementation.user.UserDetailServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class  AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @PostMapping("/login")
    private ResponseEntity<AuthResponse> loginAdmin(@Valid @RequestBody AuthLoginRequest userRequest)throws AutBadCredentialsException {
          return new ResponseEntity<>( this.userDetailServiceImpl.authenticateUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    private ResponseEntity<?> logout(){
        return null;
    }
    
}
