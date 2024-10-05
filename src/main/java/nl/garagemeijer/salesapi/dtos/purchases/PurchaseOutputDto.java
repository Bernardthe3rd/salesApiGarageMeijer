package nl.garagemeijer.salesapi.dtos.purchases;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseOutputDto {

    private Long id;
    private LocalDate orderDate;
    private String typeOrder;
    private String supplier;
    private BigDecimal purchasePriceEx;
    private BigDecimal taxPrice;
    private BigDecimal bpmPrice;
    private BigDecimal purchasePriceIncl;
    private LocalDate expectedDeliveryDate;
    private int quantity;
    private String status;
    private int orderNumber;
    private String businessOrPrivate;

}
