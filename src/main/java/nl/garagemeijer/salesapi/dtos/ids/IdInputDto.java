package nl.garagemeijer.salesapi.dtos.ids;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdInputDto {

    @NotNull(message = "please fill in a valid id number")
    private Long id;

}
