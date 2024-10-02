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

    private LocalDate saleDate;
    private BigDecimal salePrice;
    private Double discount;
    private String status;
    private String warranty;
    private int orderNumber;
    private String paymentMethod;
    private Boolean businessOrPrivate; //aan de hand van deze variabele laat je zien of er BPM en/of BTW betaald moet worden.
    private BigDecimal bpmPrice;
    private BigDecimal taxPrice;
    private String comment;
    private SignatureUpload signatureUpload;
    private Addition addition;
    private Customer customer;
    private Account salesPerson;

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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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

    public Boolean getBusinessOrPrivate() {
        return businessOrPrivate;
    }

    public void setBusinessOrPrivate(Boolean businessOrPrivate) {
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

    public SignatureUpload getSignatureUpload() {
        return signatureUpload;
    }

    public void setSignatureUpload(SignatureUpload signatureUpload) {
        this.signatureUpload = signatureUpload;
    }

    public Addition getAddition() {
        return addition;
    }

    public void setAddition(Addition addition) {
        this.addition = addition;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(Account salesPerson) {
        this.salesPerson = salesPerson;
    }
}
