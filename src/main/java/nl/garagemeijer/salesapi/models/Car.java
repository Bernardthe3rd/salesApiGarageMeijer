package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;

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

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public Double getTrunkCapacity() {
        return trunkCapacity;
    }

    public void setTrunkCapacity(Double trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }
}
