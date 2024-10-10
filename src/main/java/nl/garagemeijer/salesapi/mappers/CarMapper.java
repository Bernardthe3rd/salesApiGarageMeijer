package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.cars.CarInputDto;
import nl.garagemeijer.salesapi.dtos.cars.CarOutputDto;
import nl.garagemeijer.salesapi.models.Car;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapper {

    public CarOutputDto carToCarOutputDto(Car car) {
        var dto = new CarOutputDto();

        dto.setId(car.getId());
        dto.setVinNumber(car.getVinNumber());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setType(car.getType());
        dto.setYear(car.getYear());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setMileage(car.getMileage());
        dto.setColor(car.getColor());
        dto.setFuelType(car.getFuelType());
        dto.setEngineCapacity(car.getEngineCapacity());
        dto.setFirstRegistrationDate(car.getFirstRegistrationDate());
        dto.setAmountInStock(car.getAmountInStock());
        dto.setNumberOfDoors(car.getNumberOfDoors());
        dto.setTrunkCapacity(car.getTrunkCapacity());
        dto.setTransmission(car.getTransmission());
        dto.setSeatingCapacity(car.getSeatingCapacity());

        return dto;
    }

    public Car updateCarFromCarInputDto(CarInputDto carInputDto, Car car) {
        car.setVinNumber(carInputDto.getVinNumber());
        car.setBrand(carInputDto.getBrand());
        car.setModel(carInputDto.getModel());
        car.setType(carInputDto.getType());
        car.setYear(carInputDto.getYear());
        car.setLicensePlate(carInputDto.getLicensePlate());
        car.setMileage(carInputDto.getMileage());
        car.setColor(carInputDto.getColor());
        car.setFuelType(carInputDto.getFuelType());
        car.setEngineCapacity(carInputDto.getEngineCapacity());
        car.setFirstRegistrationDate(carInputDto.getFirstRegistrationDate());
        car.setNumberOfDoors(carInputDto.getNumberOfDoors());
        car.setTrunkCapacity(carInputDto.getTrunkCapacity());
        car.setTransmission(carInputDto.getTransmission());
        car.setSeatingCapacity(carInputDto.getSeatingCapacity());

        return car;
    }

    public Car carInputDtoToCar(CarInputDto carInputDto) {
        var car = new Car();
        return updateCarFromCarInputDto(carInputDto, car);
    }

    public List<CarOutputDto> carsToCarsOutputDtos(List<Car> cars) {
        List<CarOutputDto> carOutputDtos = new ArrayList<>();
        for (Car car : cars) {
            CarOutputDto carDto = carToCarOutputDto(car);
            carOutputDtos.add(carDto);
        }
        return carOutputDtos;
    }
}
