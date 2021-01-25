package pl.dkobylarz.garage_system_api.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarModelDto {

    private final int modelId;
    private final String modelName;
    private final int brandId;
}
