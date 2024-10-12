package nl.garagemeijer.salesapi.mappers;

import nl.garagemeijer.salesapi.dtos.purchases.PurchaseInputDto;
import nl.garagemeijer.salesapi.dtos.purchases.PurchaseOutputDto;
import nl.garagemeijer.salesapi.models.Purchase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseMapper {

    private final VehicleMapper vehicleMapper;

    public PurchaseMapper(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    public PurchaseOutputDto purchaseToPurchaseOutputDto(Purchase purchase) {
        var dto = new PurchaseOutputDto();

        dto.setId(purchase.getId());
        dto.setOrderDate(purchase.getOrderDate());
        dto.setSupplier(purchase.getSupplier());
        dto.setPurchasePriceEx(purchase.getPurchasePriceEx());
        dto.setTaxPrice(purchase.getTaxPrice());
        dto.setBpmPrice(purchase.getBpmPrice());
        dto.setPurchasePriceIncl(purchase.getPurchasePriceIncl());
        dto.setExpectedDeliveryDate(purchase.getExpectedDeliveryDate());
        dto.setQuantity(purchase.getQuantity());
        dto.setStatus(purchase.getStatus());
        dto.setOrderNumber(purchase.getOrderNumber());
        dto.setBusinessOrPrivate(purchase.getBusinessOrPrivate());
        if (purchase.getVehicle() != null) {
            dto.setVehicle(vehicleMapper.vehicleToVehicleOutputDto(purchase.getVehicle()));
        }
        if (purchase.getAdminId() != null) {
            dto.setAdminId(purchase.getAdminId());
        }

        return dto;
    }

    public Purchase updatePurchaseFromPurchaseInputDto(PurchaseInputDto purchaseInputDto, Purchase purchase) {
        purchase.setSupplier(purchaseInputDto.getSupplier());
        purchase.setPurchasePriceIncl(purchaseInputDto.getPurchasePriceIncl());
        purchase.setExpectedDeliveryDate(purchaseInputDto.getExpectedDeliveryDate());
        purchase.setQuantity(purchaseInputDto.getQuantity());
        purchase.setBusinessOrPrivate(purchaseInputDto.getBusinessOrPrivate());

        return purchase;
    }

    public Purchase purchaseInputDtoToPurchase(PurchaseInputDto purchaseInputDto) {
        var purchase = new Purchase();
        return updatePurchaseFromPurchaseInputDto(purchaseInputDto, purchase);
    }

    public List<PurchaseOutputDto> purchasesToPurchasesOutputDtos(List<Purchase> purchases) {
        List<PurchaseOutputDto> purchaseOutputDtos = new ArrayList<>();
        for (Purchase purchase : purchases) {
            PurchaseOutputDto purchaseDto = purchaseToPurchaseOutputDto(purchase);
            purchaseOutputDtos.add(purchaseDto);
        }
        return purchaseOutputDtos;
    }

}
