package pl.dkobylarz.garage_system_api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserHasActiveIssueException extends RuntimeException{
    public UserHasActiveIssueException(){super("Nie można usunać użytkownika który ma aktywne zgłoszenie");}
}
