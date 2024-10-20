package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleInputDto;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleOutputDto;
import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.mappers.BusinessVehicleMapper;
import nl.garagemeijer.salesapi.models.BusinessVehicle;
import nl.garagemeijer.salesapi.repositories.BusinessVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessVehicleService {

    private final BusinessVehicleRepository businessVehicleRepository;
    private final BusinessVehicleMapper businessVehicleMapper;

    public BusinessVehicleService(BusinessVehicleRepository businessVehicleRepository, BusinessVehicleMapper businessVehicleMapper) {
        this.businessVehicleRepository = businessVehicleRepository;
        this.businessVehicleMapper = businessVehicleMapper;
    }

    public List<BusinessVehicleOutputDto> getBusinessVehicles() {
        return businessVehicleMapper.businessVehiclesToBusinessVehiclesOutputDtos(businessVehicleRepository.findAll());
    }

    public BusinessVehicleOutputDto getBusinessVehicle(Long id) {
        Optional<BusinessVehicle> optionalBusinessVehicle = businessVehicleRepository.findById(id);
        if (optionalBusinessVehicle.isPresent()) {
            return businessVehicleMapper.businessVehicleToBusinessVehicleOutputDto(optionalBusinessVehicle.get());
        } else {
            throw new RecordNotFoundException("Business vehicle with id: " + id + " not found");
        }
    }

    public BusinessVehicleOutputDto saveBusinessVehicle(BusinessVehicleInputDto businessVehicle) {
        BusinessVehicle businessVehicleToSave = businessVehicleMapper.businessVehicleInputDtoToBusinessVehicle(businessVehicle);
        businessVehicleToSave.setAmountInStock(0);
        return businessVehicleMapper.businessVehicleToBusinessVehicleOutputDto(businessVehicleRepository.save(businessVehicleToSave));
    }

    public BusinessVehicleOutputDto updateBusinessVehicle(Long id, BusinessVehicleInputDto businessVehicle) {
        BusinessVehicle getBusinessVehicle = businessVehicleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Business vehicle with id: " + id + " not found"));
        BusinessVehicle businessVehicleToUpdate = businessVehicleMapper.updateBusinessVehicleFromBusinessVehicleInputDto(businessVehicle, getBusinessVehicle);
        return businessVehicleMapper.businessVehicleToBusinessVehicleOutputDto(businessVehicleRepository.save(businessVehicleToUpdate));
    }

    public void deleteBusinessVehicle(Long id) {
        businessVehicleRepository.deleteById(id);
    }
}
