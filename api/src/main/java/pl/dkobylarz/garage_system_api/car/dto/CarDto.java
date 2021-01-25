package pl.dkobylarz.garage_system_api.car.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeDeserializer;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeSerializer;
import pl.dkobylarz.garage_system_api.util.serialization.LocalDateToDateSerializer;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class CarDto {
    private final int carId;
    private final String carModelName;
    private final String carBrandName;
    private final int carMileage;
    private final String carPhotoUrl;
    @JsonSerialize(using = LocalDateToDateSerializer.class)
    private final LocalDateTime yearOfProduction;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private final LocalDateTime createdDate;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private final LocalDateTime lastUpdated;
}
