package nl.garagemeijer.salesapi.dtos.profiles;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.enums.Status;

@Getter
@Setter
public class ProfileInputDto {
    
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

}
