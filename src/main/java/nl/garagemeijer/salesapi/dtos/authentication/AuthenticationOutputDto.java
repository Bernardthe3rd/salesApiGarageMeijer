package nl.garagemeijer.salesapi.dtos.authentication;

import lombok.Getter;

@Getter
public class AuthenticationOutputDto {

    private final String token;
    private final String message;

    public AuthenticationOutputDto(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
