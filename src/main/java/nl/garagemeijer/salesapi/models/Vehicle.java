package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "vehicle_type")
@Table(name = "vehicles")
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vinNumber;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false, name = "vehicle_year")
    private int year;
    @Column(nullable = false)
    private String licensePlate;
    @Column(nullable = false)
    private int mileage;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String fuelType;
    @Column(nullable = false)
    private Double engineCapacity;
    @Column(nullable = false)
    private LocalDate firstRegistrationDate;
    private int amountInStock;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Sale> sales;
}
