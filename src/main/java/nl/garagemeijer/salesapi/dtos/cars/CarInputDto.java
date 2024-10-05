package nl.garagemeijer.salesapi.dtos.cars;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CarInputDto {

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
    @NotNull(message = "please fill in the amount of doors")
    private int numberOfDoors;
    @NotNull(message = "please fill in the trunk capacity in liters")
    private Double trunkCapacity;
    @NotNull(message = "please fill in automatic or manual")
    private String transmission;
    @NotNull(message = "please fill in how many passengers are allowed in the car")
    private int seatingCapacity;
}
