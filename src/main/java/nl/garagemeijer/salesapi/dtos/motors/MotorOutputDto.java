package nl.garagemeijer.salesapi.dtos.motors;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.VehicleOutputDto;

import java.time.LocalDate;

@Getter
@Setter
public class MotorOutputDto extends VehicleOutputDto {

    private String typeMotorcycle;
    private int wheelbase;
    private String handlebarType;

}
