package pl.dkobylarz.garage_system_api.car.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dkobylarz.garage_system_api.car.dto.CarBrandDto;

import java.util.Set;

@Repository
interface CarBrandRepository extends JpaRepository<CarBrand, Integer> {

    Set<CarBrandDto> findAllProjectedBy();

}
