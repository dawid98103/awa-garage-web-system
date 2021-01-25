package pl.dkobylarz.garage_system_api.infrastructure.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String userAvatarUrl;
    private String password;
    private Integer roleId;
}
