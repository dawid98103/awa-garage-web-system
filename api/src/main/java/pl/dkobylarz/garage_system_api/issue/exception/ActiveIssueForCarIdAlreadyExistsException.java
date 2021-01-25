package pl.dkobylarz.garage_system_api.issue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ActiveIssueForCarIdAlreadyExistsException extends RuntimeException{
    public ActiveIssueForCarIdAlreadyExistsException(){super("Istnieje aktywne zgłoszenie dla podanego pojazdu");}
}
