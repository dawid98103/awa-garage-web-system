package pl.dkobylarz.garage_system_api.infrastructure.security.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dkobylarz.garage_system_api.user.domain.UserFacade;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UserFacade userFacade;
    private final JwtUtils jwtUtils;

    public String generateJwtToken(Authentication authentication){
        return jwtUtils.generateJwtToken(authentication);
    }

    public String getUserNameFromJwtToken(String token){
        return jwtUtils.getUserNameFromJwtToken(token);
    }

    public boolean validateJwtToken(String authToken){
        return jwtUtils.validateJwtToken(authToken);
    }

    public void saveUser(UserDto userDto) {
        userFacade.saveUser(userDto);
    }

    public boolean existsUserByUsername(String username) {
        return userFacade.existsByUsername(username);
    }
}
