package nl.garagemeijer.salesapi.dtos.sales;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Addition;
import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleInputDto {

    @NotNull(message = "please fill in the sale price including bpm and btw")
    private BigDecimal salePriceIncl;
    @NotNull(message = "please fill in the amount of vehicles is sold")
    private int quantity;
    @NotNull(message = "please fill in a discount price")
    private Double discount;
    @NotNull(message = "please fill in how many years of warranty is on the vehicle")
    private String warranty;
    @NotNull(message = "please fill in which method the customer is going to pay")
    private String paymentMethod;
    @NotNull(message = "please fill in if the vehicle is for business or private use")
    private BusinessOrPrivate businessOrPrivate;
    @NotNull(message = "please fill in what type this is, offerte or order")
    private String typeOrder;
    private String comment;
    private Addition addition;

}
