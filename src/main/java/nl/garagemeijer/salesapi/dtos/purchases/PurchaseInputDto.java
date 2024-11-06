package nl.garagemeijer.salesapi.dtos.purchases;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseInputDto {

    @NotNull(message = "please fill in which manufacturer you are buying from")
    private String supplier;
    @NotNull(message = "please fill in the total purchase price including tax and bpm")
    private BigDecimal purchasePriceIncl;
    @NotNull(message = "please fill in on which date the car is expected to come in")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate expectedDeliveryDate;
    @NotNull(message = "please fill in how many cars we are buying of this kind")
    private Integer quantity;
    @NotNull(message = "please fill in if the car is for business use or private")
    private BusinessOrPrivate businessOrPrivate;

}
