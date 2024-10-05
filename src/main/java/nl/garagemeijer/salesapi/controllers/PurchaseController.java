package nl.garagemeijer.salesapi.controllers;

import jakarta.validation.Valid;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import nl.garagemeijer.salesapi.services.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        Purchase selectedPurchase = purchaseService.getPurchase(id);
        return ResponseEntity.ok(selectedPurchase);
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) {
        Purchase createdPurchase = purchaseService.savePurchase(purchase);
        URI location = URI.create("/api/purchases/" + createdPurchase.getId());
        return ResponseEntity.created(location).body(createdPurchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @Valid @RequestBody Purchase purchase) {
        Purchase updatedPurchase = purchaseService.updatePurchase(id, purchase);
        return ResponseEntity.ok(updatedPurchase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}
