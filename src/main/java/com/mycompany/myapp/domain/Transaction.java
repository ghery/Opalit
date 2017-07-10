package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_position")
    public Integer idPosition;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Double quantity;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @NotNull
    @Column(name = "settlement_date", nullable = false)
    private LocalDate settlementDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "market_fees")
    private Double marketFees;

    @Column(name = "broker_fees")
    private Double brokerFees;

    @Column(name = "client_fees")
    private Float clientFees;

    @Column(name = "forex_spot")
    private Double forexSpot;

    @Column(name = "comments")
    private String comments;

    @Column(name = "bo_comment")
    private String boComment;

    @Column(name = "cfd")
    private Boolean cfd;

    @ManyToOne(optional = false)
    @NotNull
    private Book book;

    @ManyToOne(optional = false)
    @NotNull
    private Asset asset;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private People broker;

    @ManyToOne
    private People primebroker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPosition() {
        return idPosition;
    }

    public Transaction idPosition(Integer idPosition) {
        this.idPosition = idPosition;
        return this;
    }

    public void setIdPosition(Integer idPosition) {
        this.idPosition = idPosition;
    }

    public Double getPrice() {
        return price;
    }

    public Transaction price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Transaction quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getType() {
        return type;
    }

    public Transaction type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public Transaction status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public Transaction tradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
        return this;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public Transaction settlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
        return this;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Transaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getMarketFees() {
        return marketFees;
    }

    public Transaction marketFees(Double marketFees) {
        this.marketFees = marketFees;
        return this;
    }

    public void setMarketFees(Double marketFees) {
        this.marketFees = marketFees;
    }

    public Double getBrokerFees() {
        return brokerFees;
    }

    public Transaction brokerFees(Double brokerFees) {
        this.brokerFees = brokerFees;
        return this;
    }

    public void setBrokerFees(Double brokerFees) {
        this.brokerFees = brokerFees;
    }

    public Float getClientFees() {
        return clientFees;
    }

    public Transaction clientFees(Float clientFees) {
        this.clientFees = clientFees;
        return this;
    }

    public void setClientFees(Float clientFees) {
        this.clientFees = clientFees;
    }

    public Double getForexSpot() {
        return forexSpot;
    }

    public Transaction forexSpot(Double forexSpot) {
        this.forexSpot = forexSpot;
        return this;
    }

    public void setForexSpot(Double forexSpot) {
        this.forexSpot = forexSpot;
    }

    public String getComments() {
        return comments;
    }

    public Transaction comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBoComment() {
        return boComment;
    }

    public Transaction boComment(String boComment) {
        this.boComment = boComment;
        return this;
    }

    public void setBoComment(String boComment) {
        this.boComment = boComment;
    }

    public Boolean isCfd() {
        return cfd;
    }

    public Transaction cfd(Boolean cfd) {
        this.cfd = cfd;
        return this;
    }

    public void setCfd(Boolean cfd) {
        this.cfd = cfd;
    }

    public Book getBook() {
        return book;
    }

    public Transaction book(Book book) {
        this.book = book;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Asset getAsset() {
        return asset;
    }

    public Transaction asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Transaction currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public People getBroker() {
        return broker;
    }

    public Transaction broker(People people) {
        this.broker = people;
        return this;
    }

    public void setBroker(People people) {
        this.broker = people;
    }

    public People getPrimebroker() {
        return primebroker;
    }

    public Transaction primebroker(People people) {
        this.primebroker = people;
        return this;
    }

    public void setPrimebroker(People people) {
        this.primebroker = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", idPosition='" + getIdPosition() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", tradeDate='" + getTradeDate() + "'" +
            ", settlementDate='" + getSettlementDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", marketFees='" + getMarketFees() + "'" +
            ", brokerFees='" + getBrokerFees() + "'" +
            ", clientFees='" + getClientFees() + "'" +
            ", forexSpot='" + getForexSpot() + "'" +
            ", comments='" + getComments() + "'" +
            ", boComment='" + getBoComment() + "'" +
            ", cfd='" + isCfd() + "'" +
            "}";
    }
}
