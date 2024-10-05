package nl.garagemeijer.salesapi.dtos.purchases;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseInputDto {

    @NotNull(message = "please fill in what type of order it is")
    private String typeOrder;
    @NotNull(message = "please fill in which manufacturer you are buying from")
    private String supplier;
    @NotNull(message = "please fill in the total purchase price including tax and bpm")
    private BigDecimal purchasePriceIncl;
    @NotNull(message = "please fill in on which date the car is expected to come in")
    private LocalDate expectedDeliveryDate;
    @NotNull(message = "please fill in how many cars we are buying of this kind")
    private int quantity;
    @NotNull(message = "please fill in if the car is for business use or private")
    private String businessOrPrivate;

}
