package pl.dkobylarz.garage_system_api.issue.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeDeserializer;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeSerializer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int issueId;

    @Column(precision = 4)
    private BigDecimal amount;

    private String description;

    private String issueNumber;

    private IssueStatus status;

    private int userId;

    private int carId;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        lastUpdated = createdDate;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
