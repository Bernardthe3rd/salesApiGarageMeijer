package nl.garagemeijer.salesapi.dtos.businessVehicles;

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
    private Boolean companyOwned;
    private Double cargoCapacity;
    private BusinessUsageType businessUsage;

}
