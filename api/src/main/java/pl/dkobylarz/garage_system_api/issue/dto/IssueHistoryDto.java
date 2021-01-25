package pl.dkobylarz.garage_system_api.issue.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeDeserializer;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class IssueHistoryDto {
    private final int issueId;
    private final String username;
    private final String carBrandName;
    private final String carModelName;
    private final String description;
    private final BigDecimal amount;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private final LocalDateTime createdDate;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private final LocalDateTime lastUpdated;
}
