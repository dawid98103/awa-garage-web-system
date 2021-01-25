package pl.dkobylarz.garage_system_api.issue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadAmountException extends RuntimeException{
    public BadAmountException(){super("Podano nieprawidłową kwotę");}
}
