package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.motors.MotorInputDto;
import nl.garagemeijer.salesapi.dtos.motors.MotorOutputDto;
import nl.garagemeijer.salesapi.models.Motor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MotorMapper {

    public MotorOutputDto motorTomotorOutputDto(Motor motor) {
        var dto = new MotorOutputDto();

        dto.setId(motor.getId());
        dto.setVinNumber(motor.getVinNumber());
        dto.setBrand(motor.getBrand());
        dto.setModel(motor.getModel());
        dto.setType(motor.getType());
        dto.setYear(motor.getYear());
        dto.setLicensePlate(motor.getLicensePlate());
        dto.setMileage(motor.getMileage());
        dto.setColor(motor.getColor());
        dto.setFuelType(motor.getFuelType());
        dto.setEngineCapacity(motor.getEngineCapacity());
        dto.setFirstRegistrationDate(motor.getFirstRegistrationDate());
        dto.setAmountInStock(motor.getAmountInStock());
        dto.setTypeMotorcycle(motor.getTypeMotorcycle());
        dto.setWheelbase(motor.getWheelbase());
        dto.setHandlebarType(motor.getHandlebarType());

        return dto;
    }

    public Motor updatemotorFrommotorInputDto(MotorInputDto motorInputDto, Motor motor) {
        motor.setVinNumber(motorInputDto.getVinNumber());
        motor.setBrand(motorInputDto.getBrand());
        motor.setModel(motorInputDto.getModel());
        motor.setType(motorInputDto.getType());
        motor.setYear(motorInputDto.getYear());
        motor.setLicensePlate(motorInputDto.getLicensePlate());
        motor.setMileage(motorInputDto.getMileage());
        motor.setColor(motorInputDto.getColor());
        motor.setFuelType(motorInputDto.getFuelType());
        motor.setEngineCapacity(motorInputDto.getEngineCapacity());
        motor.setFirstRegistrationDate(motorInputDto.getFirstRegistrationDate());
        motor.setTypeMotorcycle(motorInputDto.getTypeMotorcycle());
        motor.setWheelbase(motorInputDto.getWheelbase());
        motor.setHandlebarType(motorInputDto.getHandlebarType());

        return motor;
    }

    public Motor motorInputDtoTomotor(MotorInputDto motorInputDto) {
        var motor = new Motor();
        return updatemotorFrommotorInputDto(motorInputDto, motor);
    }

    public List<MotorOutputDto> motorsTomotorsOutputDtos(List<Motor> motors) {
        List<MotorOutputDto> motorOutputDtos = new ArrayList<>();
        for (Motor motor : motors) {
            MotorOutputDto motorDto = motorTomotorOutputDto(motor);
            motorOutputDtos.add(motorDto);
        }
        return motorOutputDtos;
    }

}
