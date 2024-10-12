package nl.garagemeijer.salesapi.dtos.cars;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.vehicles.VehicleOutputDto;

@Getter
@Setter
public class CarOutputDto extends VehicleOutputDto {

    private int numberOfDoors;
    private Double trunkCapacity;
    private String transmission;
    private int seatingCapacity;

}
