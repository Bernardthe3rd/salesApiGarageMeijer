package nl.garagemeijer.salesapi.dtos.profiles;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.models.Profile;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.models.Sale;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProfileOutputDto {

    public ProfileOutputDto(Profile profile) {
        this.id = profile.getId();
        this.creationDate = profile.getCreationDate();
        this.role = profile.getRole();
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.dateOfBirth = profile.getDateOfBirth();
        this.street = profile.getStreet();
        this.postalCode = profile.getPostalCode();
        this.city = profile.getCity();
        this.country = profile.getCountry();
        this.email = profile.getEmail();
        this.phoneNumber = profile.getPhoneNumber();
        this.saleOrders = profile.getSaleOrders();
        this.purchaseOrders = profile.getPurchaseOrders();
    }

    private Long id;
    private LocalDate creationDate;
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
    private List<Sale> saleOrders;
    private List<Purchase> purchaseOrders;
    private UserOutputDto user;

}
