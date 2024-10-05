package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name = "Purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;
    @Column(nullable = false)
    private String typeOrder;
    @Column(nullable = false)
    private String supplier;
    private BigDecimal purchasePriceEx;
    private BigDecimal taxPrice;
    private BigDecimal bpmPrice;
    @Column(nullable = false)
    private BigDecimal purchasePriceIncl;
    @Column(nullable = false)
    private LocalDate expectedDeliveryDate;
    @Column(nullable = false)
    private int quantity;
    private String status;
    private int orderNumber;
    @Column(nullable = false)
    private String businessOrPrivate; //aan de hand van deze variabele laat je zien of er BPM en/of BTW betaald moet worden.

//    private Vehicle vehicle;


}
