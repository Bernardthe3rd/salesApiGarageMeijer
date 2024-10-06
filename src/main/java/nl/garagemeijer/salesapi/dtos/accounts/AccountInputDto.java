package nl.garagemeijer.salesapi.dtos.accounts;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Status;

@Getter
@Setter
public class AccountInputDto {

    @NotNull(message = "please fill in if the account is for a salesPerson or Admin or Customer")
    private String accountType;
    @NotNull(message = "please fill in if the account is open or closed")
    private Status status;
    @NotNull(message = "please fill in a firstname")
    private String firstName;
    @NotNull(message = "please fill in a lastname")
    private String lastName;
    @NotNull(message = "please fill in the date of birt")
    private String dateOfBirth;
    @NotNull(message = "please fill in the street of the address")
    private String street;
    @NotNull(message = "please fill in the postal code of the address")
    private String postalCode;
    @NotNull(message = "please fill in the city of the address")
    private String city;
    @NotNull(message = "please fill in the country of the address")
    private String country;
    @NotNull(message = "please fill in a valid email address")
    private String email;
    @NotNull(message = "please fill a phone number in")
    private String phoneNumber;

}
