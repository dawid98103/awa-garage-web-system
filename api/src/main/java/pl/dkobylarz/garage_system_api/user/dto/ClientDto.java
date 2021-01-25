package pl.dkobylarz.garage_system_api.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import pl.dkobylarz.garage_system_api.car.dto.CarDto;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeDeserializer;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class ClientDto {
    private final int userId;
    private final String username;
    private final String userAvatar;
    private final Set<CarDto> carDtos;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private final LocalDateTime createdDate;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private final LocalDateTime lastUpdated;
}
