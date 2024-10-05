package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String prefferedContactMethod;
    private String nameLastSalesPerson;
//    private Account account;
//    private List<Vehicle> vehicles;
//    private List<Sale> purchaseHistory;

}
