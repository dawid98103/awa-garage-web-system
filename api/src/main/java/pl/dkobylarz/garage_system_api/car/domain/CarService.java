package pl.dkobylarz.garage_system_api.car.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dkobylarz.garage_system_api.car.constant.EngineType;
import pl.dkobylarz.garage_system_api.car.dto.CarBrandDto;
import pl.dkobylarz.garage_system_api.car.dto.CarDto;
import pl.dkobylarz.garage_system_api.car.dto.CarModelDto;
import pl.dkobylarz.garage_system_api.car.dto.EngineTypeDto;
import pl.dkobylarz.garage_system_api.issue.dto.CreateCarDto;
import pl.dkobylarz.garage_system_api.util.DateTimeStampConverter;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final CarBrandRepository carBrandRepository;
    private final EntityManager entityManager;

    public Set<CarDto> getCarsForUserId(int userId) {
        return carRepository.findAllByUserId(userId);
    }

    public CarDto getCarDtoById(int carId) {
        return carRepository.findCarDtoByCarId(carId);
    }

    public Set<CarModelDto> getCarModels() {
        return new HashSet<>(carModelRepository.findAllProjectedBy());
    }

    public Set<CarBrandDto> getCarBrands() {
        return new HashSet<>(carBrandRepository.findAllProjectedBy());
    }

    public Set<EngineTypeDto> getEngineTypes() {
        return Arrays.stream(EngineType.values())
                .map(engineType -> EngineTypeDto.builder()
                        .typeId(engineType.getTypeId())
                        .typeName(engineType.getName())
                        .build())
                .collect(Collectors.toSet());
    }

    public void saveCarForUser(CreateCarDto createCarDto) {
        carRepository.save(convertToEntity(createCarDto));
    }

    public void deactivateCar(int carId) {
        carRepository.deactivateCarById(carId);
    }

    private Car convertToEntity(CreateCarDto createCarDto) {
        CarBrand carBrand = entityManager.getReference(CarBrand.class, createCarDto.getCarBrandId());
        CarModel carModel = entityManager.getReference(CarModel.class, createCarDto.getCarModelId());

        return Car.builder()
                .carId(0)
                .userId(createCarDto.getUserId())
                .carMileage(createCarDto.getCarMileage())
                .carPhotoUrl(createCarDto.getCarPhotoUrl())
                .yearOfProduction(DateTimeStampConverter.localDateTimeOf(createCarDto.getYearOfProduction()))
                .carModel(carModel)
                .carBrand(carBrand)
                .engineType(EngineType.getEngineTypeByTypeId(createCarDto.getEngineTypeId()))
                .active(true)
                .build();
    }
}
