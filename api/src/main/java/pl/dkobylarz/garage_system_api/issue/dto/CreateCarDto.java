package pl.dkobylarz.garage_system_api.issue.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCarDto {

    private int userId;
    private int carBrandId;
    private int carModelId;
    private int engineTypeId;
    private String carPhotoUrl;
    private long yearOfProduction;
    private int carMileage;
}
