package nl.garagemeijer.salesapi.dtos.motors;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MotorInputDto {

    private String vinNumber;
    private String brand;
    private String model;
    private String type;
    private int year;
    private String licensePlate;
    private int mileage;
    private String color;
    private String fuelType;
    private Double engineCapacity;
    private LocalDate firstRegistrationDate;
    @NotNull(message = "please fill in what type of motor it is")
    private String typeMotorcycle;
    @NotNull(message = "please fill in how long the wheelbase is in meters")
    private int wheelbase;
    @NotNull(message = "please fill in what type of handlebar the motor has")
    private String handlebarType;

}
