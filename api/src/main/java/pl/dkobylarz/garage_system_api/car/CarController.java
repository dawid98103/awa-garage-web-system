package pl.dkobylarz.garage_system_api.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.dkobylarz.garage_system_api.car.domain.CarFacade;
import pl.dkobylarz.garage_system_api.issue.dto.CreateCarDto;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
class CarController {

    private final CarFacade carFacade;

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_KLIENT, ROLE_MECHANIK')")
    public ResponseEntity<?> createCarForUser(@RequestBody CreateCarDto createCarDto) {
        carFacade.saveCarForUser(createCarDto);
        return ResponseEntity.ok("Pomyślnie utworzono pojazd dla użytkownika");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCarsForUser(@PathVariable int userId) {
        return ResponseEntity.ok(carFacade.getCarsForUserId(userId));
    }

    @GetMapping("/brands")
    @PreAuthorize("hasRole('ROLE_KLIENT')")
    public ResponseEntity<?> getCarBrands() {
        return ResponseEntity.ok(carFacade.getCarBrands());
    }

    @GetMapping("/models")
    public ResponseEntity<?> getCarModels() {
        return ResponseEntity.ok(carFacade.getCarModels());
    }

    @GetMapping("/engine/types")
    @PreAuthorize("hasAnyRole('ROLE_KLIENT', 'ROLE_MECHANIK')")
    public ResponseEntity<?> getEngineTypes() {
        return ResponseEntity.ok(carFacade.getEngineTypes());
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<?> deactivateCar(@PathVariable int carId) {
        carFacade.deactivateCar(carId);
        return ResponseEntity.ok("Pomyślnie usunięto pojazd");
    }
}
