package nl.garagemeijer.salesapi.dtos.businessVehicles;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class BusinessVehicleInputDto {

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
    private Integer year;
    @NotNull(message = "please fill in the license plate of the vehicle")
    @Pattern(regexp = "^V.*", message = "License plate must start with V")
    private String licensePlate;
    @NotNull(message = "please fill in the mileage of the vehicle")
    private Integer mileage;
    @NotNull(message = "please fill in the color of the vehicle")
    private String color;
    @NotNull(message = "please fill in which type of fuel the vehicle needs")
    private String fuelType;
    @NotNull(message = "please fill in how much the capacity of the engine is")
    private Double engineCapacity;
    @NotNull(message = "please fill a right first registration date in. like YYYY-MM-DD")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate firstRegistrationDate;
    @NotNull(message = "please set true if the vehicle is company owned else set false")
    private Boolean companyOwned;
    @NotNull(message = "please fill in the cargo capacity in liters")
    private Double cargoCapacity;
    @NotNull(message = "please fill in the field of which business the vehicle is going to be used")
    private BusinessUsageType businessUsage;

}
