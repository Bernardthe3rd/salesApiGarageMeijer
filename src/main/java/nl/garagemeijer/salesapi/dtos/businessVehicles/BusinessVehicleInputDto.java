package nl.garagemeijer.salesapi.dtos.businessVehicles;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;

import java.time.LocalDate;

@Getter
@Setter
public class BusinessVehicleInputDto {

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
    @NotEmpty(message = "please set true if the vehicle is company owned else set false")
    private Boolean companyOwned;
    @NotNull(message = "please fill in the cargo capacity in liters")
    private Double cargoCapacity;
    @NotNull(message = "please fill in the field of which business the vehicle is going to be used")
    private BusinessUsageType businessUsage;

}
