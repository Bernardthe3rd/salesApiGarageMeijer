package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.Addition;
import nl.garagemeijer.salesapi.enums.Status;

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
    @Column(nullable = false)
    private BigDecimal salePriceIncl;
    @Column(nullable = false)
    private String typeOrder;
    private Status status;
    private int orderNumber;
    private String comment;
    @Column(nullable = false)
    private Double discount;
    @Column(nullable = false)
    private String warranty;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private String businessOrPrivate;
    private Addition addition;
    private Long sellerId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

//    private SignatureUpload signatureUpload;

}
