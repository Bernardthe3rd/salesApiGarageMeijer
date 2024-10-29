package nl.garagemeijer.salesapi.dtos.users;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordInputDto {

    @NotNull(message = "Please fill your current password in")
    private String oldPassword;
    @NotNull(message = "Please fill your new password in")
    private String newPassword;

}
