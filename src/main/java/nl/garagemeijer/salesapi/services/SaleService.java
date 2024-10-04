package nl.garagemeijer.salesapi.services;

import nl.garagemeijer.salesapi.models.Sale;
import nl.garagemeijer.salesapi.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getSales() {
        return saleRepository.findAll();
    }

    public Sale getSale(Long id) {
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if (saleOptional.isPresent()) {
            return saleOptional.get();
        } else {
            throw new RuntimeException("Sale not found");
        }
    }

    public Sale saveSale(Sale sale) {
        if (sale.getBusinessOrPrivate().contains("business")) {
            sale.setBpmPrice(new BigDecimal("0.00"));
            sale.setTaxPrice(new BigDecimal("0.00"));
            sale.setSalePriceEx(new BigDecimal(String.valueOf(sale.getSalePriceIncl())));
        } else if (sale.getBusinessOrPrivate().contains("private")) {
            BigDecimal taxRate = new BigDecimal("21.00");
            BigDecimal taxOne = sale.getSalePriceIncl().divide(new BigDecimal("121.00"), 2, RoundingMode.HALF_UP);
            BigDecimal taxTwo = taxOne.multiply(taxRate);

            sale.setTaxPrice(taxTwo);
            sale.setBpmPrice(new BigDecimal(String.valueOf(sale.getSalePriceIncl().multiply(new BigDecimal("0.1")))));
            BigDecimal saleExOne = sale.getSalePriceIncl().subtract(sale.getBpmPrice());
            sale.setSalePriceEx(saleExOne.subtract(sale.getTaxPrice()));
            if (sale.getDiscount() != null) {
                sale.setSalePriceEx(new BigDecimal(String.valueOf(sale.getSalePriceEx().subtract(BigDecimal.valueOf(sale.getDiscount())))));
            }
        }

        if (sale.getOrderNumber() == 0) {
            sale.setOrderNumber(1);
        } else {
            int getLastOrderNumber = saleRepository.findLastOrderNumber();
            sale.setOrderNumber(++getLastOrderNumber);
        }
        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, Sale sale) {
        Sale saleToUpdate = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
        saleToUpdate.setStatus(sale.getStatus());
        return saleRepository.save(saleToUpdate);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
