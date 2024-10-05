package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.cars.CarInputDto;
import nl.garagemeijer.salesapi.dtos.cars.CarOutputDto;
import nl.garagemeijer.salesapi.models.Car;
import nl.garagemeijer.salesapi.repositories.CarRepository;
import nl.garagemeijer.salesapi.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final CarRepository carRepository;

    public CarController(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
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
    public ResponseEntity<CarOutputDto> createCar(@Valid @RequestBody CarInputDto car) {
        CarOutputDto createdCar = carService.saveCar(car);
        URI location = URI.create("/api/cars/" + createdCar.getId());
        return ResponseEntity.created(location).body(createdCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarOutputDto> updateCar(@PathVariable Long id, @Valid @RequestBody CarInputDto car) {
        CarOutputDto updatedCar = carService.updateCar(id, car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
