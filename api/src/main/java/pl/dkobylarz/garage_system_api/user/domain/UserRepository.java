package pl.dkobylarz.garage_system_api.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dkobylarz.garage_system_api.user.dto.UserDto;

import java.util.Optional;
import java.util.Set;

@Repository
interface UserRepository extends JpaRepository<User, Integer> {

    Optional<UserDto> findByUsername(String username);

    @Query("select u.username from User u where u.userId = :userId")
    String findUsernameByUserId(int userId);

    @Query("select u.userAvatar from User u where u.userId = :userId")
    String findUserAvatarUrlByUserId(int userId);

    @Query("select u from User u where u.roleId = :roleId")
    Set<User> findByRoleId(int roleId);

    Optional<User> findByUserId(int id);

    boolean existsByUsername(String username);
}
