package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private Integer type;

    @ManyToOne
    private Book parent;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Calcul calcul;

    @ManyToOne
    private Asset fund;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Book name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public Book type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Book getParent() {
        return parent;
    }

    public Book parent(Book book) {
        this.parent = book;
        return this;
    }

    public void setParent(Book book) {
        this.parent = book;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Book currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Calcul getCalcul() {
        return calcul;
    }

    public Book calcul(Calcul calcul) {
        this.calcul = calcul;
        return this;
    }

    public void setCalcul(Calcul calcul) {
        this.calcul = calcul;
    }

    public Asset getFund() {
        return fund;
    }

    public Book fund(Asset asset) {
        this.fund = asset;
        return this;
    }

    public void setFund(Asset asset) {
        this.fund = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
