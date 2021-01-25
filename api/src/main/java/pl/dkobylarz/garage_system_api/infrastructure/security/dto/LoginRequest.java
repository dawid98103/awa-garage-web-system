package pl.dkobylarz.garage_system_api.infrastructure.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    private final String username;
    private final String password;
}
