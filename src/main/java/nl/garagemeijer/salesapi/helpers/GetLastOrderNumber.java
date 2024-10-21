package nl.garagemeijer.salesapi.helpers;

import nl.garagemeijer.salesapi.exceptions.BadRequestException;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.models.Sale;
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

    public Integer getLastOrderNumber(Object document) {
        switch (document) {
            case Sale sale -> {
                return getLastOrderNumberFromSale(sale);
            }
            case Purchase purchase -> {
                return getLastOrderNumberFromPurchase(purchase);
            }
            default -> throw new BadRequestException("Please enter a valid document");
        }
    }

    private Integer getLastOrderNumberFromSale(Sale sale) {
        Integer lastOrderNumber = saleRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }

    private Integer getLastOrderNumberFromPurchase(Purchase purchase) {
        Integer lastOrderNumber = purchaseRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }

}
