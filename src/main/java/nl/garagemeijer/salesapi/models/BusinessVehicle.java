package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;

@Getter
@Setter

@Entity
@Table(name = "businessVehicles")
public class BusinessVehicle extends Vehicle {

    @Column(nullable = false)
    private Boolean companyOwned;
    @Column(nullable = false)
    private Double cargoCapacity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BusinessUsageType businessUsage;

}
