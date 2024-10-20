package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleInputDto;
import nl.garagemeijer.salesapi.dtos.businessVehicles.BusinessVehicleOutputDto;
import nl.garagemeijer.salesapi.services.BusinessVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles/businessvehicles")
public class BusinessVehicleController {

    private final BusinessVehicleService businessVehicleService;

    public BusinessVehicleController(BusinessVehicleService businessVehicleService) {
        this.businessVehicleService = businessVehicleService;
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
    public ResponseEntity<BusinessVehicleOutputDto> createBusinessVehicle(@Valid @RequestBody BusinessVehicleInputDto businessVehicleInput) {
        BusinessVehicleOutputDto createdBusinessVehicle = businessVehicleService.saveBusinessVehicle(businessVehicleInput);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBusinessVehicle.getId())
                .toUri();
        System.out.println(locationDynamic);
        return ResponseEntity.created(locationDynamic).body(createdBusinessVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessVehicleOutputDto> updateBusinessVehicle(@PathVariable Long id, @Valid @RequestBody BusinessVehicleInputDto businessVehicleInput) {
        BusinessVehicleOutputDto updatedBusinessVehicle = businessVehicleService.updateBusinessVehicle(id, businessVehicleInput);
        return ResponseEntity.ok(updatedBusinessVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessVehicle(@PathVariable Long id) {
        businessVehicleService.deleteBusinessVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
