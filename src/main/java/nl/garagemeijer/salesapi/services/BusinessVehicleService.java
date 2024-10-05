package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.BusinessVehicle;
import nl.garagemeijer.salesapi.repositories.BusinessVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessVehicleService {

    private final BusinessVehicleRepository businessVehicleRepository;

    public BusinessVehicleService(BusinessVehicleRepository businessVehicleRepository) {
        this.businessVehicleRepository = businessVehicleRepository;
    }

    public List<BusinessVehicle> getBusinessVehicles() {
        return businessVehicleRepository.findAll();
    }

    public BusinessVehicle getBusinessVehicle(Long id) {
        Optional<BusinessVehicle> businessVehicleOptional = businessVehicleRepository.findById(id);
        if (businessVehicleOptional.isPresent()) {
            return businessVehicleOptional.get();
        } else {
            throw new RuntimeException("Business vehicle not found");
        }
    }

    public BusinessVehicle saveBusinessVehicle(BusinessVehicle businessVehicle) {
        return businessVehicleRepository.save(businessVehicle);
    }

    public BusinessVehicle updateBusinessVehicle(Long id, BusinessVehicle businessVehicle) {
        BusinessVehicle businessVehicleToUpdate = businessVehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Business vehicle not found"));
        businessVehicleToUpdate.setBusinessUsage(businessVehicle.getBusinessUsage());
        return businessVehicleRepository.save(businessVehicleToUpdate);
    }

    public void deleteBusinessVehicle(Long id) {
        businessVehicleRepository.deleteById(id);
    }
}
