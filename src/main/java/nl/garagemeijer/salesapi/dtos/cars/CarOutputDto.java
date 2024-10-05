package nl.garagemeijer.salesapi.dtos.cars;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CarOutputDto {

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
    private int numberOfDoors;
    private Double trunkCapacity;
    private String transmission;
    private int seatingCapacity;

}
