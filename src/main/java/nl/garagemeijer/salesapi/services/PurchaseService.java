package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }


    public List<Purchase> getPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchase(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (purchaseOptional.isPresent()) {
            return purchaseOptional.get();
        } else {
            throw new RuntimeException("Purchase not found");
        }
    }

    public Purchase savePurchase(Purchase purchase) {
        purchase.setOrderDate(LocalDate.now());
        purchase.setStatus("Open");

        return purchaseRepository.save(purchase);
    }

    public Purchase updatePurchase(Long id, Purchase purchase) {
        Purchase purchaseToUpdate = purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
        purchaseToUpdate.setStatus(purchase.getStatus());
        return purchaseRepository.save(purchaseToUpdate);
    }


    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}
