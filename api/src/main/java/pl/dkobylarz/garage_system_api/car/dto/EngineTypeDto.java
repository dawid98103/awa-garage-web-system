package pl.dkobylarz.garage_system_api.car.dto;

import lombok.*;
import pl.dkobylarz.garage_system_api.car.constant.EngineType;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EngineTypeDto {
    private final int typeId;
    private final String typeName;

    public EngineType getEnumValue(){
        return EngineType.getEngineTypeByTypeId(this.typeId);
    }
}
