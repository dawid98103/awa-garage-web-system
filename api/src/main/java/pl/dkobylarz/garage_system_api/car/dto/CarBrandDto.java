package pl.dkobylarz.garage_system_api.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarBrandDto {

    private final int brandId;
    private final String brandName;
}
