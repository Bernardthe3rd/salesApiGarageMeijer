package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.purchases.PurchaseInputDto;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseOutputDto;
import nl.garagemeijer.salesapi.enums.Status;
import nl.garagemeijer.salesapi.mappers.PurchaseMapper;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
    }


    public BigDecimal calculateTaxPrice(BigDecimal priceIncl) {
        BigDecimal taxRate = new BigDecimal("21.00");
        BigDecimal taxOne = priceIncl.divide(new BigDecimal("121.00"), 2, RoundingMode.HALF_UP);
        return taxOne.multiply(taxRate);
    }

    public BigDecimal calculateBpmPrice(BigDecimal priceIncl) {
        return priceIncl.multiply(new BigDecimal("0.1"));
    }

    public BigDecimal calculatePriceEx(BigDecimal priceIncl) {
        BigDecimal taxPrice = calculateTaxPrice(priceIncl);
        BigDecimal bpmPrice = calculateBpmPrice(priceIncl);
        return priceIncl.subtract(taxPrice).subtract(bpmPrice);
    }

    public List<BigDecimal> calculatePrices(Purchase purchase) {
        List<BigDecimal> prices = new ArrayList<>();

        if (purchase.getBusinessOrPrivate().contains("business")) {
            purchase.setBpmPrice(new BigDecimal("0.00"));
            prices.add(purchase.getBpmPrice());
            purchase.setTaxPrice(new BigDecimal("0.00"));
            prices.add(purchase.getTaxPrice());
            purchase.setPurchasePriceEx(new BigDecimal(String.valueOf(purchase.getPurchasePriceIncl())));
            prices.add(purchase.getPurchasePriceEx());
        } else if (purchase.getBusinessOrPrivate().contains("private")) {
            purchase.setTaxPrice(calculateTaxPrice(purchase.getPurchasePriceIncl()));
            prices.add(purchase.getTaxPrice());
            purchase.setBpmPrice(calculateBpmPrice(purchase.getPurchasePriceIncl()));
            prices.add(purchase.getBpmPrice());
            purchase.setPurchasePriceEx(calculatePriceEx(purchase.getPurchasePriceIncl()));
            prices.add(purchase.getPurchasePriceEx());
        } else {
            throw new IllegalArgumentException("Unsupported purchase type: " + purchase.getBusinessOrPrivate());
        }

        return prices;
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
        purchaseToSave.setStatus(Status.OPEN);
        purchaseToSave.setOrderNumber(getLastOrderNumber() + 1);

        List<BigDecimal> prices = calculatePrices(purchaseToSave);
        purchaseToSave.setTaxPrice(prices.get(0));
        purchaseToSave.setBpmPrice(prices.get(1));
        purchaseToSave.setPurchasePriceEx(prices.get(2));

        return purchaseMapper.purchaseToPurchaseOutputDto(purchaseRepository.save(purchaseToSave));
    }

    public PurchaseOutputDto updatePurchase(Long id, PurchaseInputDto purchase) {
        Purchase getPurchase = purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
        Purchase purchaseToUpdate = purchaseMapper.updatePurchaseFromPurchaseInputDto(purchase, getPurchase);

        List<BigDecimal> prices = calculatePrices(purchaseToUpdate);
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
