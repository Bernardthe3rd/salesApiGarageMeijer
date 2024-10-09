package nl.garagemeijer.salesapi.dtos.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserOutputDto {

    private Long id;
    private String username;
    private String password;
    private LocalDate lastLogin;
    private Boolean isActive;
    private LocalDate creationDate;

}
