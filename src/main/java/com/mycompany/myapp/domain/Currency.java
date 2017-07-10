package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 3)
    @Column(name = "mnemo", length = 3)
    private String mnemo;

    @Column(name = "name")
    private String name;

    @Column(name = "is_reversed")
    private Boolean isReversed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMnemo() {
        return mnemo;
    }

    public Currency mnemo(String mnemo) {
        this.mnemo = mnemo;
        return this;
    }

    public void setMnemo(String mnemo) {
        this.mnemo = mnemo;
    }

    public String getName() {
        return name;
    }

    public Currency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsReversed() {
        return isReversed;
    }

    public Currency isReversed(Boolean isReversed) {
        this.isReversed = isReversed;
        return this;
    }

    public void setIsReversed(Boolean isReversed) {
        this.isReversed = isReversed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        if (currency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", mnemo='" + getMnemo() + "'" +
            ", name='" + getName() + "'" +
            ", isReversed='" + isIsReversed() + "'" +
            "}";
    }
}
