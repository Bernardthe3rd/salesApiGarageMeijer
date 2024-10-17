package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "admin_order_numbers", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "order_number")
    private List<Integer> purchaseOrderNumbers;

    @ElementCollection
    @CollectionTable(name = "seller_order_numbers", joinColumns = @JoinColumn(name = "seller_id"))
    @Column(name = "order_number")
    private List<Integer> saleOrderNumbers;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}