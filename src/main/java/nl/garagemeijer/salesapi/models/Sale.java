package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Addition;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name = "Sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate saleDate;
    private BigDecimal salePriceEx;
    private BigDecimal bpmPrice;
    private BigDecimal taxPrice;
    private BigDecimal salePriceIncl;
    private Double discount;
    private String status;
    private String warranty;
    private int orderNumber;
    private String paymentMethod;
    private String businessOrPrivate; //aan de hand van deze variabele laat je zien of er BPM en/of BTW betaald moet worden.
    private String comment;
    //    private SignatureUpload signatureUpload;
    private Addition addition;
//    private Customer customer;
//    private Account salesPerson;

}
