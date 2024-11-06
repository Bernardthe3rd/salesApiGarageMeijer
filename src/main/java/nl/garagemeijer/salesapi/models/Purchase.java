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

    @Column(nullable = false)
    private String supplier;
    private Long adminId;
    private Integer orderNumber;
    private LocalDate orderDate;
    private BigDecimal purchasePriceEx;
    private BigDecimal taxPrice;
    private BigDecimal bpmPrice;
    @Column(nullable = false)
    private BigDecimal purchasePriceIncl;
    @Column(nullable = false)
    private LocalDate expectedDeliveryDate;
    @Column(nullable = false)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BusinessOrPrivate businessOrPrivate;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


}
