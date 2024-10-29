package nl.garagemeijer.salesapi.dtos.users;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDto {

    @NotNull(message = "Please fill a/your unique username in")
    private String username;
    @NotNull(message = "Please fill in your password")
    private String password;

}
