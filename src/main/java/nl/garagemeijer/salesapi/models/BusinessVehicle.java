package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;

@Entity
@Table(name = "BusinessVehicles")
public class BusinessVehicle extends Vehicle {

    @Column(nullable = false)
    private Boolean companyOwned;
    @Column(nullable = false)
    private Double cargoCapacity;
    @Column(nullable = false)
    private BusinessUsageType businessUsage;

    public Boolean getCompanyOwned() {
        return companyOwned;
    }

    public void setCompanyOwned(Boolean companyOwned) {
        this.companyOwned = companyOwned;
    }

    public Double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(Double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public BusinessUsageType getBusinessUsage() {
        return businessUsage;
    }

    public void setBusinessUsage(BusinessUsageType businessUsage) {
        this.businessUsage = businessUsage;
    }
}
