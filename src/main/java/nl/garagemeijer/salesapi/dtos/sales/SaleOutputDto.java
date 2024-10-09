package nl.garagemeijer.salesapi.dtos.sales;

import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Addition;
import nl.garagemeijer.salesapi.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SaleOutputDto {

    private Long id;
    private LocalDate saleDate;
    private BigDecimal salePriceEx;
    private BigDecimal bpmPrice;
    private BigDecimal taxPrice;
    private BigDecimal salePriceIncl;
    private Double discount;
    private String orderType;
    private Status status;
    private String warranty;
    private int orderNumber;
    private String paymentMethod;
    private String businessOrPrivate;
    private String comment;
    private Addition addition;

//    private SignatureUpload signatureUpload;
//    private Customer customer;
//    private Account salesPerson;
}
