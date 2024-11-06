package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "Motors")
public class Motor extends Vehicle {

    @Column(nullable = false)
    private String typeMotorcycle;
    @Column(nullable = false)
    private Integer wheelbase;
    @Column(nullable = false)
    private String handlebarType;

}
