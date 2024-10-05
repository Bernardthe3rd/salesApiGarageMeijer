package nl.garagemeijer.salesapi.dtos.customers;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInputDto {

    private String firstName;
    private String lastName;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String phoneNumber;
    private String email;
    private String dateOfBirth;
    @NotNull(message = "please fill in which method the customer would like to stay up-to-date")
    private String prefferedContactMethod;

}
