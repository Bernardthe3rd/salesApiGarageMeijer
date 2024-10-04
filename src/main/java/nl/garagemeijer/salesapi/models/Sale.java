package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;
import nl.garagemeijer.salesapi.enums.Addition;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate saleDate = LocalDate.now();
    private BigDecimal salePriceEx;
    private BigDecimal bpmPrice;
    private BigDecimal taxPrice;
    private BigDecimal salePriceIncl;
    private Double discount;
    private String status = "open";
    private String warranty;
    private int orderNumber;
    private String paymentMethod;
    private String businessOrPrivate; //aan de hand van deze variabele laat je zien of er BPM en/of BTW betaald moet worden.
    private String comment;
    //    private SignatureUpload signatureUpload;
    private Addition addition;
//    private Customer customer;
//    private Account salesPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getSalePriceEx() {
        return salePriceEx;
    }

    public void setSalePriceEx(BigDecimal salePriceEx) {
        this.salePriceEx = salePriceEx;
    }

    public BigDecimal getSalePriceIncl() {
        return salePriceIncl;
    }

    public void setSalePriceIncl(BigDecimal salePriceIncl) {
        this.salePriceIncl = salePriceIncl;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBusinessOrPrivate() {
        return businessOrPrivate;
    }

    public void setBusinessOrPrivate(String businessOrPrivate) {
        this.businessOrPrivate = businessOrPrivate;
    }

    public BigDecimal getBpmPrice() {
        return bpmPrice;
    }

    public void setBpmPrice(BigDecimal bpmPrice) {
        this.bpmPrice = bpmPrice;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Addition getAddition() {
        return addition;
    }

    public void setAddition(Addition addition) {
        this.addition = addition;
    }

}
