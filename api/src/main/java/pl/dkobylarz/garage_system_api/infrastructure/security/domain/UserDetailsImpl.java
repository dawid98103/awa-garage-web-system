package pl.dkobylarz.garage_system_api.infrastructure.security.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.dkobylarz.garage_system_api.user.constant.UserRoles;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDetailsImpl implements UserDetails {
    private int id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserDto user) {
        List<GrantedAuthority> authorities
                = Collections.singletonList(new SimpleGrantedAuthority(UserRoles.getByRoleId(user.getRoleId()).getName()));

        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
