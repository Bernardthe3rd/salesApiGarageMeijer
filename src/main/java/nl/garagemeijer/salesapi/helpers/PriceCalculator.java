package nl.garagemeijer.salesapi.helpers;

import nl.garagemeijer.salesapi.models.Purchase;
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
}
