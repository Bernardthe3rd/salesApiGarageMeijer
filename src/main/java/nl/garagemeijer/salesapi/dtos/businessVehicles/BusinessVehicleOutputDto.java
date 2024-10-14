package nl.garagemeijer.salesapi.dtos.businessVehicles;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.vehicles.VehicleOutputDto;
import nl.garagemeijer.salesapi.enums.BusinessUsageType;

@Getter
@Setter
public class BusinessVehicleOutputDto extends VehicleOutputDto {

    private Boolean companyOwned;
    private Double cargoCapacity;
    private BusinessUsageType businessUsage;

}
