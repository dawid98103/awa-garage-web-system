package pl.dkobylarz.garage_system_api.issue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IssueStatusNotFoundException extends RuntimeException{
    public IssueStatusNotFoundException(){super("Nie znaleziono statusu po podanym identyfikatorze");}
}
