package restaurante.example.demo.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String userName,
                               @NotBlank String password) {
}
