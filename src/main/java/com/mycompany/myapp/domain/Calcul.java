package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Calcul.
 */
@Entity
@Table(name = "calcul")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calcul implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marketvalue")
    private Integer marketvalue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMarketvalue() {
        return marketvalue;
    }

    public Calcul marketvalue(Integer marketvalue) {
        this.marketvalue = marketvalue;
        return this;
    }

    public void setMarketvalue(Integer marketvalue) {
        this.marketvalue = marketvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Calcul calcul = (Calcul) o;
        if (calcul.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calcul.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calcul{" +
            "id=" + getId() +
            ", marketvalue='" + getMarketvalue() + "'" +
            "}";
    }
}
