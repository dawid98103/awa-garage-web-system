//package pl.dkobylarz.garage_system_api.factory;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import pl.dkobylarz.garage_system_api.car.domain.Car;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//import java.util.Set;
//
//public class CarFactory {
//
//    private static final Random generator = new Random();
//
//    public static Car createCarWith(String carName) {
//        return Car.builder()
//                .carId(0)
//                .carName(carName)
//                .carMileage(255)
//                .carPhotoUrl("")
//                .yearOfProduction(LocalDateTime.now())
//                .build();
//    }
//
//    public static Set<Car> createRandomCars() {
//        Car car = Car.builder()
//                .carId(0)
//                .carName(RandomStringUtils.randomAlphabetic(5))
//                .carMileage(generator.nextInt(20))
//                .carPhotoUrl(RandomStringUtils.randomAlphabetic(5))
//                .yearOfProduction(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now())
//                .build();
//
//        Car car1 = Car.builder()
//                .carId(0)
//                .carName(RandomStringUtils.randomAlphabetic(5))
//                .carMileage(generator.nextInt(20))
//                .carPhotoUrl(RandomStringUtils.randomAlphabetic(5))
//                .yearOfProduction(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now())
//                .build();
//
//        Car car2 = Car.builder()
//                .carId(0)
//                .carName(RandomStringUtils.randomAlphabetic(5))
//                .carMileage(generator.nextInt(20))
//                .carPhotoUrl(RandomStringUtils.randomAlphabetic(5))
//                .yearOfProduction(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now())
//                .build();
//
//        return Set.of(car, car1, car2);
//    }
//}
