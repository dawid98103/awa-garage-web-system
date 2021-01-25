package pl.dkobylarz.garage_system_api.car.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EngineType {
    PETROL_ENGINE(1, "Benzyna"),
    LPG_WITH_PETROL_ENGINE(2, "LPG+Benzyna"),
    DIESEL_ENGINE(3, "Diesel"),
    LPG(4, "LPG");

    private final int typeId;
    private final String name;

    public static EngineType getEngineTypeByTypeId(int typeId) {
        return Arrays.stream(values())
                .filter(engineType -> engineType.getTypeId() == typeId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
