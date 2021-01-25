package pl.dkobylarz.garage_system_api.car.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dkobylarz.garage_system_api.car.constant.EngineType;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeDeserializer;
import pl.dkobylarz.garage_system_api.util.serialization.CustomLocalDateTimeSerializer;
import pl.dkobylarz.garage_system_api.util.serialization.LocalDateToDateSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int carId;

    private int userId;

    private int carMileage;

    private String carPhotoUrl;

    @JsonSerialize(using = LocalDateToDateSerializer.class)
    private LocalDateTime yearOfProduction;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;

    private EngineType engineType;

    private boolean active;

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
