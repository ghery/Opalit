package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A HCurrency.
 */
@Entity
@Table(name = "h_currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "op")
    private Double op;

    @Column(name = "hi")
    private Double hi;

    @Column(name = "lo")
    private Double lo;

    @Column(name = "la")
    private Double la;

    @NotNull
    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Column(name = "vol")
    private Float vol;

    @Column(name = "pc")
    private Double pc;

    @NotNull
    @Column(name = "jhi_time", nullable = false)
    private ZonedDateTime time;

    @ManyToOne
    private Currency collector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOp() {
        return op;
    }

    public HCurrency op(Double op) {
        this.op = op;
        return this;
    }

    public void setOp(Double op) {
        this.op = op;
    }

    public Double getHi() {
        return hi;
    }

    public HCurrency hi(Double hi) {
        this.hi = hi;
        return this;
    }

    public void setHi(Double hi) {
        this.hi = hi;
    }

    public Double getLo() {
        return lo;
    }

    public HCurrency lo(Double lo) {
        this.lo = lo;
        return this;
    }

    public void setLo(Double lo) {
        this.lo = lo;
    }

    public Double getLa() {
        return la;
    }

    public HCurrency la(Double la) {
        this.la = la;
        return this;
    }

    public void setLa(Double la) {
        this.la = la;
    }

    public LocalDate getDay() {
        return day;
    }

    public HCurrency day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Float getVol() {
        return vol;
    }

    public HCurrency vol(Float vol) {
        this.vol = vol;
        return this;
    }

    public void setVol(Float vol) {
        this.vol = vol;
    }

    public Double getPc() {
        return pc;
    }

    public HCurrency pc(Double pc) {
        this.pc = pc;
        return this;
    }

    public void setPc(Double pc) {
        this.pc = pc;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public HCurrency time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Currency getCollector() {
        return collector;
    }

    public HCurrency collector(Currency currency) {
        this.collector = currency;
        return this;
    }

    public void setCollector(Currency currency) {
        this.collector = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HCurrency hCurrency = (HCurrency) o;
        if (hCurrency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hCurrency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HCurrency{" +
            "id=" + getId() +
            ", op='" + getOp() + "'" +
            ", hi='" + getHi() + "'" +
            ", lo='" + getLo() + "'" +
            ", la='" + getLa() + "'" +
            ", day='" + getDay() + "'" +
            ", vol='" + getVol() + "'" +
            ", pc='" + getPc() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
