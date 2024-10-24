package nl.garagemeijer.salesapi;

import nl.garagemeijer.salesapi.dtos.cars.CarInputDto;
import nl.garagemeijer.salesapi.dtos.cars.CarOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.CarMapper;
import nl.garagemeijer.salesapi.models.Car;
import nl.garagemeijer.salesapi.repositories.CarRepository;
import nl.garagemeijer.salesapi.services.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceUnitTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarService carService;

    @Test
    @DisplayName("Get all cars")
    public void getAllCars() {
//        Arrange
        Car car1 = new Car();
        car1.setId(1L);
        car1.setNumberOfDoors(5);
        Car car2 = new Car();
        car2.setId(2L);
        car2.setNumberOfDoors(4);

        CarOutputDto dto1 = new CarOutputDto();
        dto1.setId(1L);
        dto1.setNumberOfDoors(5);
        CarOutputDto dto2 = new CarOutputDto();
        dto2.setId(2L);
        dto2.setNumberOfDoors(4);

        List<Car> cars = Arrays.asList(car1, car2);
        List<CarOutputDto> dtos = Arrays.asList(dto1, dto2);

//        Mock repos
        when(carRepository.findAll()).thenReturn(cars);
        when(carMapper.carsToCarsOutputDtos(cars)).thenReturn(dtos);

//        Act
        List<CarOutputDto> result = carService.getCars();

//        Assert
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

    }

    @Test
    @DisplayName("Get a car by id - found")
    public void getCarByIdFound() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setNumberOfDoors(5);

        CarOutputDto dto1 = new CarOutputDto();
        dto1.setId(1L);
        dto1.setNumberOfDoors(5);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carMapper.carToCarOutputDto(car)).thenReturn(dto1);

        CarOutputDto result = carService.getCar(carId);

        assertNotNull(result);
        assertEquals(dto1, result);
    }

    @Test
    @DisplayName("Get a car by id - not found")
    public void getCarByIdNotFound() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> carService.getCar(carId));
        assertEquals("Car with id: " + carId + " not found", exception.getMessage());

        verify(carRepository).findById(carId);
        verifyNoMoreInteractions(carMapper);
    }

    @Test
    @DisplayName("Create a new car")
    public void createNewCar() {
        CarInputDto carInput = new CarInputDto();
        carInput.setVinNumber("LAGER123456789GERG");
        carInput.setBrand("Toyota");
        carInput.setModel("Yaris");
        carInput.setType("High executive");
        carInput.setYear(2024);
        carInput.setLicensePlate("P-673-GD");
        carInput.setMileage(20000);
        carInput.setColor("Red");
        carInput.setFuelType("Hybrid");
        carInput.setEngineCapacity(1.5);
        carInput.setFirstRegistrationDate(LocalDate.of(2024, 1, 2));

        Car carToSave = new Car();
        carToSave.setId(1L);
        carToSave.setVinNumber("LAGER123456789GERG");
        carToSave.setBrand("Toyota");
        carToSave.setModel("Yaris");
        carToSave.setType("High executive");
        carToSave.setYear(2024);
        carToSave.setLicensePlate("P-673-GD");
        carToSave.setMileage(20000);
        carToSave.setColor("Red");
        carToSave.setFuelType("Hybrid");
        carToSave.setEngineCapacity(1.5);
        carToSave.setFirstRegistrationDate(LocalDate.of(2024, 1, 2));

        CarOutputDto output = new CarOutputDto();
        output.setId(1L);

        when(carMapper.carInputDtoToCar(carInput)).thenReturn(carToSave);
        when(carRepository.save(carToSave)).thenReturn(carToSave);
        when(carMapper.carToCarOutputDto(carToSave)).thenReturn(output);

        CarOutputDto result = carService.saveCar(carInput);

        assertNotNull(result);
        assertEquals(output, result);

    }

    @Test
    @DisplayName("Update an existing car - found")
    public void updateFoundCar() {
        Long carId = 1L;
        CarInputDto carInput = new CarInputDto();
        carInput.setVinNumber("LAGER123456789GERG");
        carInput.setBrand("Toyota");
        carInput.setModel("Yaris");
        carInput.setType("High executive");
        carInput.setYear(2024);
        carInput.setLicensePlate("P-673-GD");
        carInput.setMileage(20000);
        carInput.setColor("Red");
        carInput.setFuelType("Hybrid");
        carInput.setEngineCapacity(1.5);
        carInput.setFirstRegistrationDate(LocalDate.of(2024, 1, 2));

        Car existingCar = new Car();
        existingCar.setId(carId);
        carInput.setVinNumber("LAGER123456789GERG");
        carInput.setBrand("Toyota");
        carInput.setModel("Yaris");
        carInput.setType("High executive");
        carInput.setYear(1999);
        carInput.setLicensePlate("P-673-GD");
        carInput.setMileage(220000);
        carInput.setColor("Black");
        carInput.setFuelType("Diesel");
        carInput.setEngineCapacity(1.5);
        carInput.setFirstRegistrationDate(LocalDate.of(1999, 1, 2));

        Car updatedCar = new Car();
        updatedCar.setId(carId);
        updatedCar.setVinNumber(carInput.getVinNumber());
        updatedCar.setBrand(carInput.getBrand());
        updatedCar.setModel(carInput.getModel());
        updatedCar.setType(updatedCar.getType());
        updatedCar.setYear(updatedCar.getYear());
        updatedCar.setLicensePlate(carInput.getLicensePlate());
        updatedCar.setMileage(updatedCar.getMileage());
        updatedCar.setColor(carInput.getColor());
        updatedCar.setFuelType(carInput.getFuelType());
        updatedCar.setEngineCapacity(updatedCar.getEngineCapacity());
        updatedCar.setFirstRegistrationDate(updatedCar.getFirstRegistrationDate());

        CarOutputDto output = new CarOutputDto();
        output.setId(updatedCar.getId());

        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));
        when(carMapper.updateCarFromCarInputDto(carInput, existingCar)).thenReturn(updatedCar);
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);
        when(carMapper.carToCarOutputDto(updatedCar)).thenReturn(output);

        CarOutputDto result = carService.updateCar(carId, carInput);

        assertNotNull(result);
        assertEquals(output, result);
    }

    @Test
    @DisplayName("Update an existing car - not found")
    public void updateNotFoundCar() {
        Long carId = 1L;
        CarInputDto carInput = new CarInputDto();

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> carService.updateCar(carId, carInput));

        assertEquals("Car with id: " + carId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Delete a car - found")
    public void deleteFoundCar() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        carService.deleteCar(carId);
    }

    @Test
    @DisplayName("Delete a car - not found")
    public void deleteNotFoundCar() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> carService.deleteCar(carId));

        assertEquals("Car with id: " + carId + " not found", exception.getMessage());
    }

}
