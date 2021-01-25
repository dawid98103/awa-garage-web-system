package pl.dkobylarz.garage_system_api.issue.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.dkobylarz.garage_system_api.issue.exception.IssueStatusNotFoundException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum IssueStatus {
    WAITING("Oczekujące", true, 1),
    ACCEPTED("Przyjęto", true, 2),
    IN_PROGRESS("W trakcie realizacji...", true, 3),
    TO_RECEIVED("Do odbioru", true, 4),
    FINISHED("Ukończono", false, 5);

    @JsonValue
    private final String statusMessage;
    private final boolean active;
    private final int statusId;

    public static IssueStatus getStatusByStatusId(int statusId) {
        return Arrays.stream(values())
                .filter(issueStatus -> issueStatus.getStatusId() == statusId)
                .findFirst()
                .orElseThrow(IssueStatusNotFoundException::new);
    }

    public static Set<IssueStatus> getActiveIssueStatus() {
        return Arrays.stream(values())
                .filter(issueStatus -> issueStatus.active)
                .collect(Collectors.toSet());
    }
}
