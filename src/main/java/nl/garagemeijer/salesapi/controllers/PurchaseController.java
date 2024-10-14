package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.dtos.ids.IdInputDto;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseInputDto;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseOutputDto;
import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import nl.garagemeijer.salesapi.services.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseRepository purchaseRepository;

    public PurchaseController(PurchaseService purchaseService, PurchaseRepository purchaseRepository) {
        this.purchaseService = purchaseService;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOutputDto>> getAllPurchases() {
        List<PurchaseOutputDto> purchases = purchaseService.getPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOutputDto> getPurchaseById(@PathVariable Long id) {
        PurchaseOutputDto selectedPurchase = purchaseService.getPurchase(id);
        return ResponseEntity.ok(selectedPurchase);
    }

    @PostMapping
    public ResponseEntity<PurchaseOutputDto> createPurchase(@Valid @RequestBody PurchaseInputDto purchase) {
        PurchaseOutputDto createdPurchase = purchaseService.savePurchase(purchase);
        URI locationDynamic = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPurchase.getId())
                .toUri();
        return ResponseEntity.created(locationDynamic).body(createdPurchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOutputDto> updatePurchase(@PathVariable Long id, @Valid @RequestBody PurchaseInputDto purchase) {
        PurchaseOutputDto updatedPurchase = purchaseService.updatePurchase(id, purchase);
        return ResponseEntity.ok(updatedPurchase);
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<PurchaseOutputDto> addVehicleToPurchase(@PathVariable Long id, @Valid @RequestBody IdInputDto vehicleId) {
        PurchaseOutputDto updatedPurchase = purchaseService.assignVehicleToPurchase(id, vehicleId);
        return ResponseEntity.ok(updatedPurchase);
    }

    @PutMapping("/{id}/admin")
    public ResponseEntity<PurchaseOutputDto> addAdminToPurchase(@PathVariable Long id, @Valid @RequestBody IdInputDto adminId) {
        PurchaseOutputDto updatedPurchase = purchaseService.assignAdminToPurchase(id, adminId);
        return ResponseEntity.ok(updatedPurchase);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}
