package pl.dkobylarz.garage_system_api.car.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dkobylarz.garage_system_api.car.dto.CarModelDto;

import java.util.Set;

@Repository
interface CarModelRepository extends JpaRepository<CarModel, Integer> {

    Set<CarModelDto> findAllProjectedBy();

}
