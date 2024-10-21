package nl.garagemeijer.salesapi.dtos.users;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.profiles.ProfileOutputDto;
import nl.garagemeijer.salesapi.models.Profile;

import java.time.LocalDate;

@Getter
@Setter
public class UserOutputDto {

    private Long id;
    private String username;
    private LocalDate lastLogin;
    private Boolean isActive;
    private LocalDate creationDate;
    private ProfileOutputDto profile;

}
