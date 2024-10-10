package nl.garagemeijer.salesapi.dtos.customers;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInputDto {

    @NotNull(message = "please fill in customers firstname")
    private String firstName;
    @NotNull(message = "please fill in customers lastname")
    private String lastName;
    @NotNull(message = "please fill in the street of the address")
    private String street;
    @NotNull(message = "please fill in the postalcode of the address")
    private String postalCode;
    @NotNull(message = "please fill in the city of the address")
    private String city;
    @NotNull(message = "please fill in the country of the address")
    private String country;
    @NotNull(message = "please fill in a phonenumber")
    private String phoneNumber;
    @NotNull(message = "please fill in the email address of the customer")
    private String email;
    @NotNull(message = "please fill in the date of birth")
    private String dateOfBirth;
    @NotNull(message = "please fill in which method the customer would like to stay up-to-date")
    private String prefferedContactMethod;

}
