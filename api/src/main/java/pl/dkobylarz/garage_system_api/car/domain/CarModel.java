package pl.dkobylarz.garage_system_api.car.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int modelId;

    private String modelName;

    private int brandId;
}
