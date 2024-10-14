package nl.garagemeijer.salesapi.helpers;

import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.models.Sale;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class PriceCalculator {

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

    public List<BigDecimal> calculatePricesPurchases(Purchase purchase) {
        List<BigDecimal> prices = new ArrayList<>();

        if (purchase.getBusinessOrPrivate() == BusinessOrPrivate.BUSINESS) {
            purchase.setBpmPrice(new BigDecimal("0.00"));
            prices.add(purchase.getBpmPrice());
            purchase.setTaxPrice(new BigDecimal("0.00"));
            prices.add(purchase.getTaxPrice());
            purchase.setPurchasePriceEx(new BigDecimal(String.valueOf(purchase.getPurchasePriceIncl())));
            prices.add(purchase.getPurchasePriceEx());
        } else if (purchase.getBusinessOrPrivate() == BusinessOrPrivate.PRIVATE) {
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

    public List<BigDecimal> calculatePricesSales(Sale sale) {
        List<BigDecimal> prices = new ArrayList<>();

        if (sale.getBusinessOrPrivate() == BusinessOrPrivate.BUSINESS) {
            sale.setBpmPrice(new BigDecimal("0.00"));
            prices.add(sale.getBpmPrice());
            sale.setTaxPrice(new BigDecimal("0.00"));
            prices.add(sale.getTaxPrice());
            sale.setSalePriceEx(new BigDecimal(String.valueOf(sale.getSalePriceIncl())));
            prices.add(sale.getSalePriceEx());
        } else if (sale.getBusinessOrPrivate() == BusinessOrPrivate.PRIVATE) {
            sale.setTaxPrice(calculateTaxPrice(sale.getSalePriceIncl()));
            prices.add(sale.getTaxPrice());
            sale.setBpmPrice(calculateBpmPrice(sale.getSalePriceIncl()));
            prices.add(sale.getBpmPrice());
            sale.setSalePriceEx(calculatePriceEx(sale.getSalePriceIncl()));
            prices.add(sale.getSalePriceEx());
        } else {
            throw new IllegalArgumentException("Unsupported sale type: " + sale.getBusinessOrPrivate());
        }

        return prices;
    }
}
