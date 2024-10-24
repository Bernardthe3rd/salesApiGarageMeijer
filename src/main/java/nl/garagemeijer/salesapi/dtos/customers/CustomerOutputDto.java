package nl.garagemeijer.salesapi.dtos.customers;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.sales.SaleOutputDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CustomerOutputDto {

    private Long id;
    private LocalDate creationDate;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;
    private String prefferedContactMethod;
    private String nameLastSalesPerson;
    private List<SaleOutputDto> purchaseHistory;

}

