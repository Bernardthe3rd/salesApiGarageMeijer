package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.dtos.purchases.PurchaseInputDto;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseOutputDto;
import nl.garagemeijer.salesapi.mappers.PurchaseMapper;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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

    public Integer getLastOrderNumber() {
        Integer lastOrderNumber = purchaseRepository.findLastOrderNumber();
        return (lastOrderNumber != null) ? lastOrderNumber : 0;
    }

    public PurchaseOutputDto savePurchase(PurchaseInputDto purchase) {
        Purchase purchaseToSave = purchaseMapper.purchaseInputDtoToPurchase(purchase);

        purchaseToSave.setOrderDate(LocalDate.now());
        purchaseToSave.setStatus("Open");

        if (purchaseToSave.getBusinessOrPrivate().contains("business")) {
            purchaseToSave.setBpmPrice(new BigDecimal("0.00"));
            purchaseToSave.setTaxPrice(new BigDecimal("0.00"));
            purchaseToSave.setPurchasePriceEx(new BigDecimal(String.valueOf(purchaseToSave.getPurchasePriceIncl())));
        } else if (purchaseToSave.getBusinessOrPrivate().contains("private")) {
            BigDecimal taxRate = new BigDecimal("21.00");
            BigDecimal taxOne = purchaseToSave.getPurchasePriceIncl().divide(new BigDecimal("121.00"), 2, RoundingMode.HALF_UP);
            BigDecimal taxTwo = taxOne.multiply(taxRate);

            purchaseToSave.setTaxPrice(taxTwo);
            purchaseToSave.setBpmPrice(new BigDecimal(String.valueOf(purchaseToSave.getPurchasePriceIncl().multiply(new BigDecimal("0.1")))));
            BigDecimal purchaseExOne = purchaseToSave.getPurchasePriceIncl().subtract(purchaseToSave.getBpmPrice());
            purchaseToSave.setPurchasePriceEx(purchaseExOne.subtract(purchaseToSave.getTaxPrice()));
        }

        purchaseToSave.setOrderNumber(getLastOrderNumber() + 1);

        return purchaseMapper.purchaseToPurchaseOutputDto(purchaseRepository.save(purchaseToSave));
    }

    public PurchaseOutputDto updatePurchase(Long id, PurchaseInputDto purchase) {
        Purchase getPurchase = purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
        Purchase purchaseToUpdate = purchaseMapper.updatePurchaseFromPurchaseInputDto(purchase, getPurchase);
        return purchaseMapper.purchaseToPurchaseOutputDto(purchaseRepository.save(purchaseToUpdate));
    }


    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}
