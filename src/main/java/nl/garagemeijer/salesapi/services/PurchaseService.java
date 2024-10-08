package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.purchases.PurchaseInputDto;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseOutputDto;
import nl.garagemeijer.salesapi.enums.Status;
import nl.garagemeijer.salesapi.helpers.PriceCalculator;
import nl.garagemeijer.salesapi.mappers.PurchaseMapper;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final PriceCalculator priceCalculator;

    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper, PriceCalculator priceCalculator) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
        this.priceCalculator = priceCalculator;
    }

    public Integer getLastOrderNumber() {
        Integer lastOrderNumber = purchaseRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }


    public List<PurchaseOutputDto> getPurchases() {
        return purchaseMapper.purchasesToPurchasesOutputDtos(purchaseRepository.findAll());
    }

    public PurchaseOutputDto getPurchase(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (purchaseOptional.isPresent()) {
            return purchaseMapper.purchaseToPurchaseOutputDto(purchaseOptional.get());
        } else {
            throw new RuntimeException("Purchase not found");
        }
    }

    public PurchaseOutputDto savePurchase(PurchaseInputDto purchase) {
        Purchase purchaseToSave = purchaseMapper.purchaseInputDtoToPurchase(purchase);

        purchaseToSave.setOrderDate(LocalDate.now());
        purchaseToSave.setStatus(Status.NEW);
        purchaseToSave.setOrderNumber(getLastOrderNumber() + 1);

        List<BigDecimal> prices = priceCalculator.calculatePricesPurchases(purchaseToSave);
        purchaseToSave.setTaxPrice(prices.get(0));
        purchaseToSave.setBpmPrice(prices.get(1));
        purchaseToSave.setPurchasePriceEx(prices.get(2));

        return purchaseMapper.purchaseToPurchaseOutputDto(purchaseRepository.save(purchaseToSave));
    }

    public PurchaseOutputDto updatePurchase(Long id, PurchaseInputDto purchase) {
        Purchase getPurchase = purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
        Purchase purchaseToUpdate = purchaseMapper.updatePurchaseFromPurchaseInputDto(purchase, getPurchase);

        List<BigDecimal> prices = priceCalculator.calculatePricesPurchases(purchaseToUpdate);
        purchaseToUpdate.setTaxPrice(prices.get(0));
        purchaseToUpdate.setBpmPrice(prices.get(1));
        purchaseToUpdate.setPurchasePriceEx(prices.get(2));

        if (purchaseToUpdate.getStatus() == Status.OPEN && purchaseToUpdate.getExpectedDeliveryDate().isBefore(LocalDate.now())) {
            purchaseToUpdate.setStatus(Status.CLOSED);
        } else {
            purchaseToUpdate.setStatus(Status.PENDING);
        }

        return purchaseMapper.purchaseToPurchaseOutputDto(purchaseRepository.save(purchaseToUpdate));
    }


    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}
