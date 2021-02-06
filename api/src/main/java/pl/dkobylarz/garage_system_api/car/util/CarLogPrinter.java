package pl.dkobylarz.garage_system_api.car.util;

import lombok.extern.slf4j.Slf4j;
import pl.dkobylarz.garage_system_api.infrastructure.logs.Logger;

@Slf4j
public class CarLogPrinter implements Logger {

    @Override
    public void printInfo(String message) {
        log.info(String.format("[POJAZDY] - %s", message));
    }

    @Override
    public void printWarn(String message) {
        log.warn(String.format("[POJAZDY] - %s", message));
    }
}
