package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.Car;
import nl.garagemeijer.salesapi.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car getCar(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return carOptional.get();
        } else {
            throw new RuntimeException("Car not found");
        }
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car car) {
        Car getCar = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        getCar.setTransmission(car.getTransmission());
//        straks mapper etc toevoegen hiervoor
        return carRepository.save(getCar);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
