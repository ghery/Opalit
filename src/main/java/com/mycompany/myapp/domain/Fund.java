package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Fund.
 */
@Entity
@Table(name = "fund")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset fund;

    @ManyToOne
    private Book mainFolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getFund() {
        return fund;
    }

    public Fund fund(Asset asset) {
        this.fund = asset;
        return this;
    }

    public void setFund(Asset asset) {
        this.fund = asset;
    }

    public Book getMainFolio() {
        return mainFolio;
    }

    public Fund mainFolio(Book book) {
        this.mainFolio = book;
        return this;
    }

    public void setMainFolio(Book book) {
        this.mainFolio = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fund fund = (Fund) o;
        if (fund.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fund.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fund{" +
            "id=" + getId() +
            "}";
    }
}
