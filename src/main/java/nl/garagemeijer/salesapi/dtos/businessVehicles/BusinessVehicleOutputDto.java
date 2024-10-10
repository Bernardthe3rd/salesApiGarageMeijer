package nl.garagemeijer.salesapi.dtos.businessVehicles;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.VehicleOutputDto;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;

import java.time.LocalDate;

@Getter
@Setter
public class BusinessVehicleOutputDto extends VehicleOutputDto {

    private Boolean companyOwned;
    private Double cargoCapacity;
    private BusinessUsageType businessUsage;

}
