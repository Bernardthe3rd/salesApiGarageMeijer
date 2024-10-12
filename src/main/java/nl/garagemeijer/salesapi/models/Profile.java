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
@Table(name = "Profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String dateOfBirth;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
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