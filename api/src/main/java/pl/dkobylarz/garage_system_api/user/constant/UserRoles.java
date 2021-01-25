package pl.dkobylarz.garage_system_api.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoles {
    CLIENT(1, "ROLE_KLIENT"),
    CAR_MECHANIC(2, "ROLE_MECHANIK"),
    UNKNOWN(3, "ROLE_NIEZNANY");

    private final int roleId;
    private final String name;

    public static UserRoles getByRoleId(int roleId) {
        for (UserRoles role : values()) {
            if (role.roleId == roleId)
                return role;
        }
        return UNKNOWN;
    }
}
