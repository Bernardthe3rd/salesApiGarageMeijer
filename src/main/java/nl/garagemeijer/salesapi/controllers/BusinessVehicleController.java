package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<BusinessVehicle>> getAllBusinessVehicles() {
        List<BusinessVehicle> businessVehicles = businessVehicleService.getBusinessVehicles();
        return ResponseEntity.ok(businessVehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessVehicle> getBusinessVehicleById(@PathVariable Long id) {
        BusinessVehicle businessVehicle = businessVehicleService.getBusinessVehicle(id);
        return ResponseEntity.ok(businessVehicle);
    }

    @PostMapping
    public ResponseEntity<BusinessVehicle> createBusinessVehicle(@Valid @RequestBody BusinessVehicle businessVehicle) {
        BusinessVehicle createdBusinessVehicle = businessVehicleService.saveBusinessVehicle(businessVehicle);
        URI location = URI.create("/api/businessvehicles/" + createdBusinessVehicle.getId());
        return ResponseEntity.created(location).body(createdBusinessVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessVehicle> updateBusinessVehicle(@PathVariable Long id, @Valid @RequestBody BusinessVehicle businessVehicle) {
        BusinessVehicle updatedBusinessVehicle = businessVehicleService.updateBusinessVehicle(id, businessVehicle);
        return ResponseEntity.ok(updatedBusinessVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessVehicle(@PathVariable Long id) {
        businessVehicleService.deleteBusinessVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
