package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.cars.CarInputDto;
import nl.garagemeijer.salesapi.dtos.cars.CarOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.CarMapper;
import nl.garagemeijer.salesapi.models.Car;
import nl.garagemeijer.salesapi.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public List<CarOutputDto> getCars() {
        return carMapper.carsToCarsOutputDtos(carRepository.findAll());
    }

    public CarOutputDto getCar(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            return carMapper.carToCarOutputDto(optionalCar.get());
        } else {
            throw new RecordNotFoundException("Car with id: " + id + " not found");
        }
    }

    public CarOutputDto saveCar(CarInputDto car) {
        Car carToSave = carMapper.carInputDtoToCar(car);
        carToSave.setAmountInStock(0);
        return carMapper.carToCarOutputDto(carRepository.save(carToSave));
    }

    public CarOutputDto updateCar(Long id, CarInputDto car) {
        Car getCar = carRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Car with id: " + id + " not found"));
        Car carToUpdate = carMapper.updateCarFromCarInputDto(car, getCar);
        return carMapper.carToCarOutputDto(carRepository.save(carToUpdate));
    }

    public void deleteCar(Long id) {
        if (carRepository.findById(id).isEmpty()) {
            throw new RecordNotFoundException("Car with id: " + id + " not found");
        }
        carRepository.deleteById(id);
    }
}
