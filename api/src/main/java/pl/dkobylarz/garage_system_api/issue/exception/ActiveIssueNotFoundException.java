package pl.dkobylarz.garage_system_api.issue.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActiveIssueNotFoundException extends RuntimeException {
    public ActiveIssueNotFoundException() {
        super("Nie znaleziono aktywnego zgłoszenia dla podanego numeru!");
    }
}
