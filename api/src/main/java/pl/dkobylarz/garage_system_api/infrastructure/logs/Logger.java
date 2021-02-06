package pl.dkobylarz.garage_system_api.infrastructure.logs;

public interface Logger {
    void printInfo(String message);
    void printWarn(String message);
}
