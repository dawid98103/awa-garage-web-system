package pl.dkobylarz.garage_system_api.car.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dkobylarz.garage_system_api.car.dto.CarDto;

import java.util.Set;

@Repository
interface CarRepository extends JpaRepository<Car, Integer> {

    @Modifying
    @Query("update Car c SET c.active = false where c.carId = :carId")
    void deactivateCarById(int carId);

    @Query("select new pl.dkobylarz.garage_system_api.car.dto.CarDto(c.carId, c.carModel.modelName, c.carBrand.brandName, c.carMileage, c.carPhotoUrl, c.yearOfProduction, c.createdDate, c.lastUpdated) from Car c where c.userId = :userId")
    Set<CarDto> findAllByUserId(int userId);

    @Query("select new pl.dkobylarz.garage_system_api.car.dto.CarDto(c.carId, c.carModel.modelName, c.carBrand.brandName, c.carMileage, c.carPhotoUrl, c.yearOfProduction, c.createdDate, c.lastUpdated) from Car c where c.carId = :carId")
    CarDto findCarDtoByCarId(int carId);
}
