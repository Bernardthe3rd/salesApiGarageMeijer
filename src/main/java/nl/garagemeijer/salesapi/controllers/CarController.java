package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.cars.CarInputDto;
import nl.garagemeijer.salesapi.dtos.cars.CarOutputDto;
import nl.garagemeijer.salesapi.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarOutputDto>> getAllCars() {
        List<CarOutputDto> cars = carService.getCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarOutputDto> getCarById(@PathVariable Long id) {
        CarOutputDto car = carService.getCar(id);
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<CarOutputDto> createCar(@Valid @RequestBody CarInputDto carInput) {
        CarOutputDto createdCar = carService.saveCar(carInput);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCar.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarOutputDto> updateCar(@PathVariable Long id, @Valid @RequestBody CarInputDto carInput) {
        CarOutputDto updatedCar = carService.updateCar(id, carInput);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
