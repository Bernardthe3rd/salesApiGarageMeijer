package nl.garagemeijer.salesapi.helpers;

import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import nl.garagemeijer.salesapi.repositories.SaleRepository;
import org.springframework.stereotype.Component;

@Component
public class GetLastOrderNumber {

    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;

    public GetLastOrderNumber(SaleRepository saleRepository, PurchaseRepository purchaseRepository) {
        this.saleRepository = saleRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Integer forSale() {
        Integer lastOrderNumber = saleRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }

    public Integer forPurchase() {
        Integer lastOrderNumber = purchaseRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }
}
