package pl.dkobylarz.garage_system_api.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dkobylarz.garage_system_api.car.domain.CarFacade;
import pl.dkobylarz.garage_system_api.user.dto.ClientDto;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;
import pl.dkobylarz.garage_system_api.user.exception.UserNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final CarFacade carFacade;

    public User getUserById(int userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Optional<UserDto> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public String getUsernameByUserId(int userId) {
        return userRepository.findUsernameByUserId(userId);
    }

    public Set<ClientDto> getUsersByRoleId(int roleId) {
        Set<User> users = userRepository.findByRoleId(roleId);
        return convertUsersToClients(users);
    }

    public String getUserAvatarUrl(int userId) {
        return userRepository.findUserAvatarUrlByUserId(userId);
    }

    public void saveUser(UserDto userDto) {
        userRepository.save(convertUserDtoToEntity(userDto));
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private Set<ClientDto> convertUsersToClients(Set<User> users) {
        return users.stream().map(user ->
                ClientDto.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .userAvatar(user.getUserAvatar())
                        .carDtos(carFacade.getCarsForUserId(user.getUserId()))
                        .createdDate(user.getCreatedDate())
                        .lastUpdated(user.getLastUpdated())
                        .build()
        ).collect(Collectors.toSet());
    }

    private User convertUserDtoToEntity(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .userAvatar(userDto.getUserAvatar())
                .roleId(userDto.getRoleId())
                .build();
    }
}
