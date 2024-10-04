package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prefferedContactMethod;
    private String nameLastSalesPerson;
//    private Account account;
//    private List<Vehicle> vehicles;
//    private List<Sale> purchaseHistory;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefferedContactMethod() {
        return prefferedContactMethod;
    }

    public void setPrefferedContactMethod(String prefferedContactMethod) {
        this.prefferedContactMethod = prefferedContactMethod;
    }
}
