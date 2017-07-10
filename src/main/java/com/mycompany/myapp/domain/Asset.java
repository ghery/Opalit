package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reference")
    private String reference;

    @Column(name = "jhi_type")
    private Integer type;

    @Column(name = "quotity")
    private Double quotity;

    @Lob
    @Column(name = "comments")
    private byte[] comments;

    @Column(name = "comments_content_type")
    private String commentsContentType;

    @Column(name = "gdate")
    private LocalDate gdate;

    @Column(name = "gnumbera")
    private Integer gnumbera;

    @Column(name = "genuma")
    private Integer genuma;

    @Column(name = "gnumberb")
    private Integer gnumberb;

    @Column(name = "gdateb")
    private LocalDate gdateb;

    @Column(name = "genumb")
    private Integer genumb;

    @Column(name = "genumc")
    private Integer genumc;

    @Column(name = "genumd")
    private Integer genumd;

    @Column(name = "gdatec")
    private LocalDate gdatec;

    @ManyToOne
    private Market market;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Country country;

    @ManyToOne
    private City city;

    @ManyToOne
    private Sector sector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Asset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public Asset reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getType() {
        return type;
    }

    public Asset type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getQuotity() {
        return quotity;
    }

    public Asset quotity(Double quotity) {
        this.quotity = quotity;
        return this;
    }

    public void setQuotity(Double quotity) {
        this.quotity = quotity;
    }

    public byte[] getComments() {
        return comments;
    }

    public Asset comments(byte[] comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(byte[] comments) {
        this.comments = comments;
    }

    public String getCommentsContentType() {
        return commentsContentType;
    }

    public Asset commentsContentType(String commentsContentType) {
        this.commentsContentType = commentsContentType;
        return this;
    }

    public void setCommentsContentType(String commentsContentType) {
        this.commentsContentType = commentsContentType;
    }

    public LocalDate getGdate() {
        return gdate;
    }

    public Asset gdate(LocalDate gdate) {
        this.gdate = gdate;
        return this;
    }

    public void setGdate(LocalDate gdate) {
        this.gdate = gdate;
    }

    public Integer getGnumbera() {
        return gnumbera;
    }

    public Asset gnumbera(Integer gnumbera) {
        this.gnumbera = gnumbera;
        return this;
    }

    public void setGnumbera(Integer gnumbera) {
        this.gnumbera = gnumbera;
    }

    public Integer getGenuma() {
        return genuma;
    }

    public Asset genuma(Integer genuma) {
        this.genuma = genuma;
        return this;
    }

    public void setGenuma(Integer genuma) {
        this.genuma = genuma;
    }

    public Integer getGnumberb() {
        return gnumberb;
    }

    public Asset gnumberb(Integer gnumberb) {
        this.gnumberb = gnumberb;
        return this;
    }

    public void setGnumberb(Integer gnumberb) {
        this.gnumberb = gnumberb;
    }

    public LocalDate getGdateb() {
        return gdateb;
    }

    public Asset gdateb(LocalDate gdateb) {
        this.gdateb = gdateb;
        return this;
    }

    public void setGdateb(LocalDate gdateb) {
        this.gdateb = gdateb;
    }

    public Integer getGenumb() {
        return genumb;
    }

    public Asset genumb(Integer genumb) {
        this.genumb = genumb;
        return this;
    }

    public void setGenumb(Integer genumb) {
        this.genumb = genumb;
    }

    public Integer getGenumc() {
        return genumc;
    }

    public Asset genumc(Integer genumc) {
        this.genumc = genumc;
        return this;
    }

    public void setGenumc(Integer genumc) {
        this.genumc = genumc;
    }

    public Integer getGenumd() {
        return genumd;
    }

    public Asset genumd(Integer genumd) {
        this.genumd = genumd;
        return this;
    }

    public void setGenumd(Integer genumd) {
        this.genumd = genumd;
    }

    public LocalDate getGdatec() {
        return gdatec;
    }

    public Asset gdatec(LocalDate gdatec) {
        this.gdatec = gdatec;
        return this;
    }

    public void setGdatec(LocalDate gdatec) {
        this.gdatec = gdatec;
    }

    public Market getMarket() {
        return market;
    }

    public Asset market(Market market) {
        this.market = market;
        return this;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Asset currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Country getCountry() {
        return country;
    }

    public Asset country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public Asset city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Sector getSector() {
        return sector;
    }

    public Asset sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asset asset = (Asset) o;
        if (asset.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), asset.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", reference='" + getReference() + "'" +
            ", type='" + getType() + "'" +
            ", quotity='" + getQuotity() + "'" +
            ", comments='" + getComments() + "'" +
            ", commentsContentType='" + commentsContentType + "'" +
            ", gdate='" + getGdate() + "'" +
            ", gnumbera='" + getGnumbera() + "'" +
            ", genuma='" + getGenuma() + "'" +
            ", gnumberb='" + getGnumberb() + "'" +
            ", gdateb='" + getGdateb() + "'" +
            ", genumb='" + getGenumb() + "'" +
            ", genumc='" + getGenumc() + "'" +
            ", genumd='" + getGenumd() + "'" +
            ", gdatec='" + getGdatec() + "'" +
            "}";
    }
}
