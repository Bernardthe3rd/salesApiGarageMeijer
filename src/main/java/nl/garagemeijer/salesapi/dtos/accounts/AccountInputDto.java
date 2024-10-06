package nl.garagemeijer.salesapi.dtos.accounts;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInputDto {

    private String accountType;
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
