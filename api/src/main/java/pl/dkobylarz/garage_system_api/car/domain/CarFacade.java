package pl.dkobylarz.garage_system_api.car.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dkobylarz.garage_system_api.car.dto.CarBrandDto;
import pl.dkobylarz.garage_system_api.car.dto.CarDto;
import pl.dkobylarz.garage_system_api.car.dto.CarModelDto;
import pl.dkobylarz.garage_system_api.car.dto.EngineTypeDto;
import pl.dkobylarz.garage_system_api.issue.dto.CreateCarDto;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CarFacade {

    private final CarService carService;

    public void saveCarForUser(CreateCarDto createCarDto) {
        carService.saveCarForUser(createCarDto);
    }

    public CarDto getCarDtoById(int carId) {
        return carService.getCarDtoById(carId);
    }

    public Set<CarDto> getCarsForUserId(int userId) {
        return carService.getCarsForUserId(userId);
    }

    public Set<CarBrandDto> getCarBrands() {
        return carService.getCarBrands();
    }

    public Set<CarModelDto> getCarModels() {
        return carService.getCarModels();
    }

    public Set<EngineTypeDto> getEngineTypes() {
        return carService.getEngineTypes();
    }

    public void deactivateCar(int carId) {
        carService.deactivateCar(carId);
    }
}
