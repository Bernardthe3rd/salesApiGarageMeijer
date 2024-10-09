package nl.garagemeijer.salesapi.dtos.customers;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerOutputDto {

    private Long id;
    private LocalDate creationDate;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;
    private String prefferedContactMethod;
    private String nameLastSalesPerson;
//    private int accountId;
//    private vehicle;
//    private sale purchasehistory;

}

