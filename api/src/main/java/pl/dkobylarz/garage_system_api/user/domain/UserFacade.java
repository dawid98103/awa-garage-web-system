package pl.dkobylarz.garage_system_api.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dkobylarz.garage_system_api.user.dto.ClientDto;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public void saveUser(UserDto userDto) {
        userService.saveUser(userDto);
    }

    public Optional<UserDto> getUserByUsername(String username){
        return userService.getUserByUsername(username);
    }

    public Set<ClientDto> getUsersByRoleId(int roleId) {
        return userService.getUsersByRoleId(roleId);
    }

    public String getUserAvatarUrl(int userId) {
        return userService.getUserAvatarUrl(userId);
    }

    public String getUsernameByUserId(int userId) {
        return userService.getUsernameByUserId(userId);
    }

    public User getUserById(int userId) {
        return userService.getUserById(userId);
    }

    public boolean existsByUsername(String username) {
        return userService.existsUserByUsername(username);
    }

    public void deactivateUser(int userId) {
        userService.deleteUserById(userId);
    }
}
