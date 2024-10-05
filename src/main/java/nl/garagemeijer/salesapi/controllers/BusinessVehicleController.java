package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleInputDto;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleOutputDto;
import nl.garagemeijer.salesapi.models.BusinessVehicle;
import nl.garagemeijer.salesapi.models.Car;
import nl.garagemeijer.salesapi.repositories.BusinessVehicleRepository;
import nl.garagemeijer.salesapi.services.BusinessVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/businessvehicles")
public class BusinessVehicleController {

    private final BusinessVehicleService businessVehicleService;
    private final BusinessVehicleRepository businessVehicleRepository;

    public BusinessVehicleController(BusinessVehicleService businessVehicleService, BusinessVehicleRepository businessVehicleRepository) {
        this.businessVehicleService = businessVehicleService;
        this.businessVehicleRepository = businessVehicleRepository;
    }

    @GetMapping
    public ResponseEntity<List<BusinessVehicleOutputDto>> getAllBusinessVehicles() {
        List<BusinessVehicleOutputDto> businessVehicles = businessVehicleService.getBusinessVehicles();
        return ResponseEntity.ok(businessVehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessVehicleOutputDto> getBusinessVehicleById(@PathVariable Long id) {
        BusinessVehicleOutputDto businessVehicle = businessVehicleService.getBusinessVehicle(id);
        return ResponseEntity.ok(businessVehicle);
    }

    @PostMapping
    public ResponseEntity<BusinessVehicleOutputDto> createBusinessVehicle(@Valid @RequestBody BusinessVehicleInputDto businessVehicle) {
        BusinessVehicleOutputDto createdBusinessVehicle = businessVehicleService.saveBusinessVehicle(businessVehicle);
        URI location = URI.create("/api/businessvehicles/" + createdBusinessVehicle.getId());
        return ResponseEntity.created(location).body(createdBusinessVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessVehicleOutputDto> updateBusinessVehicle(@PathVariable Long id, @Valid @RequestBody BusinessVehicleInputDto businessVehicle) {
        BusinessVehicleOutputDto updatedBusinessVehicle = businessVehicleService.updateBusinessVehicle(id, businessVehicle);
        return ResponseEntity.ok(updatedBusinessVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessVehicle(@PathVariable Long id) {
        businessVehicleService.deleteBusinessVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
