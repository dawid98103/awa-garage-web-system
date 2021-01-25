package pl.dkobylarz.garage_system_api.infrastructure.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final int userId;
    private final String accessToken;
    private final String username;
    private final List<String> roles;

    public static JwtResponse build(int userId, String accessToken, String username, List<String> roles) {
        return new JwtResponse(userId, accessToken, username, roles);
    }
}
