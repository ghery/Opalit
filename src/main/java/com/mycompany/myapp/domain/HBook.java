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
 * A HBook.
 */
@Entity
@Table(name = "h_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HBook implements Serializable {

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
    private Book collector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOp() {
        return op;
    }

    public HBook op(Double op) {
        this.op = op;
        return this;
    }

    public void setOp(Double op) {
        this.op = op;
    }

    public Double getHi() {
        return hi;
    }

    public HBook hi(Double hi) {
        this.hi = hi;
        return this;
    }

    public void setHi(Double hi) {
        this.hi = hi;
    }

    public Double getLo() {
        return lo;
    }

    public HBook lo(Double lo) {
        this.lo = lo;
        return this;
    }

    public void setLo(Double lo) {
        this.lo = lo;
    }

    public Double getLa() {
        return la;
    }

    public HBook la(Double la) {
        this.la = la;
        return this;
    }

    public void setLa(Double la) {
        this.la = la;
    }

    public LocalDate getDay() {
        return day;
    }

    public HBook day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Float getVol() {
        return vol;
    }

    public HBook vol(Float vol) {
        this.vol = vol;
        return this;
    }

    public void setVol(Float vol) {
        this.vol = vol;
    }

    public Double getPc() {
        return pc;
    }

    public HBook pc(Double pc) {
        this.pc = pc;
        return this;
    }

    public void setPc(Double pc) {
        this.pc = pc;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public HBook time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Book getCollector() {
        return collector;
    }

    public HBook collector(Book book) {
        this.collector = book;
        return this;
    }

    public void setCollector(Book book) {
        this.collector = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HBook hBook = (HBook) o;
        if (hBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HBook{" +
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
