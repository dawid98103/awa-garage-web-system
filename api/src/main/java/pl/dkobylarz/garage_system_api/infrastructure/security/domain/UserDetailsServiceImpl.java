package pl.dkobylarz.garage_system_api.infrastructure.security.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dkobylarz.garage_system_api.user.domain.UserFacade;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;

@Service
@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {
    private final UserFacade userFacade;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userFacade.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika o nicku: " + username));

        return UserDetailsImpl.build(user);
    }
}
