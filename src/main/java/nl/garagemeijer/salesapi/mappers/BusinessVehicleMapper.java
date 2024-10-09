package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleInputDto;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleOutputDto;
import nl.garagemeijer.salesapi.models.BusinessVehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessVehicleMapper {

    public BusinessVehicleOutputDto businessVehicleToBusinessVehicleOutputDto(BusinessVehicle businessVehicle) {
        var dto = new BusinessVehicleOutputDto();

        dto.setId(businessVehicle.getId());
        dto.setVinNumber(businessVehicle.getVinNumber());
        dto.setBrand(businessVehicle.getBrand());
        dto.setModel(businessVehicle.getModel());
        dto.setType(businessVehicle.getType());
        dto.setYear(businessVehicle.getYear());
        dto.setLicensePlate(businessVehicle.getLicensePlate());
        dto.setMileage(businessVehicle.getMileage());
        dto.setColor(businessVehicle.getColor());
        dto.setFuelType(businessVehicle.getFuelType());
        dto.setEngineCapacity(businessVehicle.getEngineCapacity());
        dto.setFirstRegistrationDate(businessVehicle.getFirstRegistrationDate());
        dto.setCompanyOwned(businessVehicle.getCompanyOwned());
        dto.setCargoCapacity(businessVehicle.getCargoCapacity());
        dto.setBusinessUsage(businessVehicle.getBusinessUsage());

        return dto;
    }

    public BusinessVehicle updateBusinessVehicleFromBusinessVehicleInputDto(BusinessVehicleInputDto businessVehicleInputDto, BusinessVehicle businessVehicle) {
        businessVehicle.setVinNumber(businessVehicleInputDto.getVinNumber());
        businessVehicle.setBrand(businessVehicleInputDto.getBrand());
        businessVehicle.setModel(businessVehicleInputDto.getModel());
        businessVehicle.setType(businessVehicleInputDto.getType());
        businessVehicle.setYear(businessVehicleInputDto.getYear());
        businessVehicle.setLicensePlate(businessVehicleInputDto.getLicensePlate());
        businessVehicle.setMileage(businessVehicleInputDto.getMileage());
        businessVehicle.setColor(businessVehicleInputDto.getColor());
        businessVehicle.setFuelType(businessVehicleInputDto.getFuelType());
        businessVehicle.setEngineCapacity(businessVehicleInputDto.getEngineCapacity());
        businessVehicle.setFirstRegistrationDate(businessVehicleInputDto.getFirstRegistrationDate());
        businessVehicle.setCompanyOwned(businessVehicleInputDto.getCompanyOwned());
        businessVehicle.setCargoCapacity(businessVehicleInputDto.getCargoCapacity());
        businessVehicle.setBusinessUsage(businessVehicleInputDto.getBusinessUsage());

        return businessVehicle;
    }

    public BusinessVehicle businessVehicleInputDtoToBusinessVehicle(BusinessVehicleInputDto businessVehicleInputDto) {
        var businessVehicle = new BusinessVehicle();
        return updateBusinessVehicleFromBusinessVehicleInputDto(businessVehicleInputDto, businessVehicle);
    }

    public List<BusinessVehicleOutputDto> businessVehiclesToBusinessVehiclesOutputDtos(List<BusinessVehicle> businessVehicles) {
        List<BusinessVehicleOutputDto> businessVehicleOutputDtos = new ArrayList<>();
        for (BusinessVehicle businessVehicle : businessVehicles) {
            BusinessVehicleOutputDto businessVehicleDto = businessVehicleToBusinessVehicleOutputDto(businessVehicle);
            businessVehicleOutputDtos.add(businessVehicleDto);
        }
        return businessVehicleOutputDtos;
    }
}
