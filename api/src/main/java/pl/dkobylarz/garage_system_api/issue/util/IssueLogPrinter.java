package pl.dkobylarz.garage_system_api.issue.util;

import lombok.extern.slf4j.Slf4j;
import pl.dkobylarz.garage_system_api.infrastructure.logs.Logger;

@Slf4j
public class IssueLogPrinter implements Logger {

    @Override
    public void printInfo(String message) {
        log.info(String.format("[SPRAWY] - %s", message));
    }

    @Override
    public void printWarn(String message) {
        log.warn(String.format("[SPRAWY] - %s", message));
    }
}
