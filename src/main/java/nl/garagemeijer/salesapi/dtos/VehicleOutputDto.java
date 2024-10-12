package nl.garagemeijer.salesapi.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;

import java.time.LocalDate;

@Getter
@Setter
public class VehicleOutputDto {

    private Long id;
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
    private int amountInStock;

//    private int numberOfDoors;
//    private Double trunkCapacity;
//    private String transmission;
//    private int seatingCapacity;
//
//    private Boolean companyOwned;
//    private Double cargoCapacity;
//    private BusinessUsageType businessUsage;
//
//    private String typeMotorcycle;
//    private int wheelbase;
//    private String handlebarType;

}
