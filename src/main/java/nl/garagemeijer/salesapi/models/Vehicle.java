package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "vehicle_type")
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
