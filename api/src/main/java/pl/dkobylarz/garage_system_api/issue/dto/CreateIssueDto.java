package pl.dkobylarz.garage_system_api.issue.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateIssueDto {
    private final int userId;
    private final int carId;
    private final String description;
}
