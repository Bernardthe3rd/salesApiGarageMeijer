package nl.garagemeijer.salesapi.dtos.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordInputDto {

    private String oldPassword;
    private String newPassword;

}
