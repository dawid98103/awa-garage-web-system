package pl.dkobylarz.garage_system_api.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dkobylarz.garage_system_api.infrastructure.security.domain.AuthenticationFacade;
import pl.dkobylarz.garage_system_api.infrastructure.security.domain.UserDetailsImpl;
import pl.dkobylarz.garage_system_api.infrastructure.security.dto.JwtResponse;
import pl.dkobylarz.garage_system_api.infrastructure.security.dto.LoginRequest;
import pl.dkobylarz.garage_system_api.infrastructure.security.dto.MessageResponse;
import pl.dkobylarz.garage_system_api.infrastructure.security.dto.SignupRequest;
import pl.dkobylarz.garage_system_api.user.constant.UserRoles;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = authenticationFacade.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.build(userDetails.getId(), jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (authenticationFacade.existsUserByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nazwa użytkownika zajęta"));
        }

        // Create new user's account
        UserDto user = UserDto.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .userAvatar(signUpRequest.getUserAvatarUrl())
                .build();

        user.setRoleId((signUpRequest.getRoleId() == null ? UserRoles.CLIENT.getRoleId() : UserRoles.CAR_MECHANIC.getRoleId()));

        authenticationFacade.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("Rejestracja przebiegła pomyślnie"));
    }
}

