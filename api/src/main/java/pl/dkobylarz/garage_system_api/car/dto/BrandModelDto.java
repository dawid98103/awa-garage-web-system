package pl.dkobylarz.garage_system_api.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class BrandModelDto {
    Set<CarBrandDto> carBrands;
    Set<CarModelDto> carModels;
}
