package nl.garagemeijer.salesapi.dtos.accounts;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AccountOutputDto {

    private Long id;
    private String accountType;
    private LocalDate creationDate;
    private String status;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;

}
