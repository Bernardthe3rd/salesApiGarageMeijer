package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.VehicleOutputDto;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleOutputDto;
import nl.garagemeijer.salesapi.dtos.cars.CarOutputDto;
import nl.garagemeijer.salesapi.dtos.customers.CustomerOutputDto;
import nl.garagemeijer.salesapi.dtos.motors.MotorOutputDto;
import nl.garagemeijer.salesapi.models.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleMapper {

    public static VehicleOutputDto vehicleToVehicleOutputDto(Vehicle vehicle) {
        VehicleOutputDto dto;

        switch (vehicle) {
            case Car car -> {
                CarOutputDto carOutputDto = new CarOutputDto();
                carOutputDto.setNumberOfDoors(car.getNumberOfDoors());
                carOutputDto.setTransmission(car.getTransmission());
                carOutputDto.setTrunkCapacity(car.getTrunkCapacity());
                carOutputDto.setSeatingCapacity(car.getSeatingCapacity());
                dto = carOutputDto;
            }
            case Motor motor -> {
                MotorOutputDto motorOutputDto = new MotorOutputDto();
                motorOutputDto.setTypeMotorcycle(motor.getTypeMotorcycle());
                motorOutputDto.setWheelbase(motor.getWheelbase());
                motorOutputDto.setHandlebarType(motor.getHandlebarType());
                dto = motorOutputDto;
            }
            case BusinessVehicle businessVehicle -> {
                BusinessVehicleOutputDto businessVehicleOutputDto = new BusinessVehicleOutputDto();
                businessVehicleOutputDto.setCompanyOwned(businessVehicle.getCompanyOwned());
                businessVehicleOutputDto.setBusinessUsage(businessVehicle.getBusinessUsage());
                businessVehicleOutputDto.setCargoCapacity(businessVehicle.getCargoCapacity());
                dto = businessVehicleOutputDto;
            }
            case null, default -> dto = new VehicleOutputDto();
        }

        dto.setId(vehicle.getId());
        dto.setVinNumber(vehicle.getVinNumber());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setType(vehicle.getType());
        dto.setYear(vehicle.getYear());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setMileage(vehicle.getMileage());
        dto.setColor(vehicle.getColor());
        dto.setFuelType(vehicle.getFuelType());
        dto.setEngineCapacity(vehicle.getEngineCapacity());
        dto.setFirstRegistrationDate(vehicle.getFirstRegistrationDate());
        dto.setAmountInStock(vehicle.getAmountInStock());

        return dto;
    }

//    public static List<VehicleOutputDto> vehiclesToVehiclesOutputDtos(List<Vehicle> vehicles) {
//        List<VehicleOutputDto> vehicleOutputDtos = new ArrayList<>();
//        for (Vehicle vehicle : vehicles) {
//            VehicleOutputDto vehicleOutputDto = vehicleToVehicleOutputDto(vehicle);
//            vehicleOutputDtos.add(vehicleOutputDto);
//        }
//        return vehicleOutputDtos;
//    }

}
