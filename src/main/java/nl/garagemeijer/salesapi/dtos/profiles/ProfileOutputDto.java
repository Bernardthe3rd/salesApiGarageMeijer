package nl.garagemeijer.salesapi.dtos.profiles;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.users.UserOutputDto;
import nl.garagemeijer.salesapi.enums.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProfileOutputDto {

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
    private List<Integer> saleOrders;
    private List<Integer> purchaseOrderNumbers;
    private UserOutputDto user;

}
