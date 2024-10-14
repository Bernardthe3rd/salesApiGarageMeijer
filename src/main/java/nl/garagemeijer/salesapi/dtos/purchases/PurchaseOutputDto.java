package nl.garagemeijer.salesapi.dtos.purchases;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.dtos.vehicles.VehicleOutputDto;
import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;
import nl.garagemeijer.salesapi.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseOutputDto {

    private Long id;
    private LocalDate orderDate;
    private String supplier;
    private BigDecimal purchasePriceEx;
    private BigDecimal taxPrice;
    private BigDecimal bpmPrice;
    private BigDecimal purchasePriceIncl;
    private LocalDate expectedDeliveryDate;
    private int quantity;
    private Status status;
    private int orderNumber;
    private BusinessOrPrivate businessOrPrivate;
    private VehicleOutputDto vehicle;
    private Long adminId;

}
