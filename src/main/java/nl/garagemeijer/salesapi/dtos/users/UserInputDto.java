package nl.garagemeijer.salesapi.dtos.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDto {

    @NotNull(message = "Please fill a/your unique username in")
    @Size(max = 64, message = "Your username must be under 64 characters long")
    private String username;
    @NotNull(message = "Please fill in your password")
    @Size(min = 8, message = "Your password has to be at least 8 characters long")
    private String password;

}
