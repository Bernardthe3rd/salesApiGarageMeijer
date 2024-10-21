package nl.garagemeijer.salesapi.dtos.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationInputDto {

    private String username;
    private String password;

}
