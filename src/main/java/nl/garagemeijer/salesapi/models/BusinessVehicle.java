package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "BusinessVehicles")
public class BusinessVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean companyOwned;
    private Double cargoCapacity;
    private String businessUsage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBusinessUsage() {
        return businessUsage;
    }

    public void setBusinessUsage(String businessUsage) {
        this.businessUsage = businessUsage;
    }
}
