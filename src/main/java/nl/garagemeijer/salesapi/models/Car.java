package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Cars")
public class Car extends Vehicle {

    @Column(nullable = false)
    private int numberOfDoors;
    @Column(nullable = false)
    private Double trunkCapacity;
    @Column(nullable = false)
    private String transmission;
    @Column(nullable = false)
    private int seatingCapacity;

}
