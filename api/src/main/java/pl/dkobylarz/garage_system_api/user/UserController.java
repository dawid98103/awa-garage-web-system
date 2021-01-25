package pl.dkobylarz.garage_system_api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.dkobylarz.garage_system_api.user.domain.UserFacade;
import pl.dkobylarz.garage_system_api.user.dto.ResponseMessageDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @GetMapping("/roles/{roleId}")
    @PreAuthorize("hasAnyRole('ROLE_MECHANIK')")
    public ResponseEntity<?> getUsersByRoleId(@PathVariable int roleId) {
        return ResponseEntity.ok(userFacade.getUsersByRoleId(roleId));
    }

    @GetMapping("/{userId}/avatar")
    @PreAuthorize("hasAnyRole('ROLE_KLIENT, ROLE_MECHANIK')")
    public ResponseEntity<?> getUserAvatar(@PathVariable int userId) {
        return ResponseEntity.ok(userFacade.getUserAvatarUrl(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        return ResponseEntity.ok(userFacade.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_MECHANIK')")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userFacade.deactivateUser(userId);
        return ResponseEntity.ok(new ResponseMessageDto("Pomyślnie usunięto użytkownika"));
    }
}
