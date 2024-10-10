package nl.garagemeijer.salesapi.dtos.profiles;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> saleOrders;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> purchaseOrderNumbers;
    private UserOutputDto user;

}
