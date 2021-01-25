package pl.dkobylarz.garage_system_api.user.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeDeserializer;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @NotNull
    @Size(max = 50)
    private String username;

    @NotNull
    @Size(max = 120)
    private String password;

    @Email
    private String email;

    private String userAvatar;

    private int roleId;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(nullable = false)
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
