package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.garagemeijer.salesapi.enums.BusinessOrPrivate;
import nl.garagemeijer.salesapi.enums.Status;

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
    private Status status;
    private int orderNumber;
    @Column(nullable = false)
    private BusinessOrPrivate businessOrPrivate;
    private Long adminId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


}
