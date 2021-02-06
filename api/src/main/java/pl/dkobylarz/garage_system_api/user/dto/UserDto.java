package pl.dkobylarz.garage_system_api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String userAvatar;
    private int roleId;
}
