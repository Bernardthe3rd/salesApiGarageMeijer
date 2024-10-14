package nl.garagemeijer.salesapi.dtos.cars;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class CarInputDto {

    @NotNull(message = "please fill a VIN number in")
    private String vinNumber;
    @NotNull(message = "please fil in the brand of the vehicle")
    private String brand;
    @NotNull(message = "please fill in the model of the vehicle")
    private String model;
    @NotNull(message = "please fill in the type of the vehicle")
    private String type;
    @NotNull(message = "please fill in the year in which the vehicle is build")
    @Min(value = 1886, message = "Year must be no earlier than 1886")
    @Max(value = 2024, message = "Year must be no later than 2024")
    private int year;
    @NotNull(message = "please fill in the license plate of the vehicle")
    private String licensePlate;
    @NotNull(message = "please fill in the mileage of the vehicle")
    private int mileage;
    @NotNull(message = "please fill in the color of the vehicle")
    private String color;
    @NotNull(message = "please fill in which type of fuel the vehicle needs")
    private String fuelType;
    @NotNull(message = "please fill in how much the capacity of the engine is")
    private Double engineCapacity;
    @NotNull(message = "please fill a right first registration date in. like YYYY-MM-DD")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
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
