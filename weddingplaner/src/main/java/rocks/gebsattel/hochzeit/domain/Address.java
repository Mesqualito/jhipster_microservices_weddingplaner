package rocks.gebsattel.hochzeit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import rocks.gebsattel.hochzeit.domain.enumeration.AddressType;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;

    /**
     * addressLine1 = street
     */
    @ApiModelProperty(value = "addressLine1 = street")
    @Column(name = "address_line_1")
    private String addressLine1;

    /**
     * addressLine2 = additional address information
     */
    @ApiModelProperty(value = "addressLine2 = additional address information")
    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "business_phone_nr")
    private String businessPhoneNr;

    @Column(name = "private_phone_nr")
    private String privatePhoneNr;

    @Column(name = "mobile_phone_nr")
    private String mobilePhoneNr;

    @OneToOne(mappedBy = "privateAddress")
    @JsonIgnore
    private WeddingGuest guestPrivate;

    @OneToOne(mappedBy = "businessAddress")
    @JsonIgnore
    private WeddingGuest guestBusiness;

    @OneToOne(mappedBy = "businessAddress")
    @JsonIgnore
    private WeddingService serviceBusiness;

    @OneToOne(mappedBy = "privateAddress")
    @JsonIgnore
    private WeddingService servicePrivate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public Address addressType(AddressType addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public Address addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Address addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessPhoneNr() {
        return businessPhoneNr;
    }

    public Address businessPhoneNr(String businessPhoneNr) {
        this.businessPhoneNr = businessPhoneNr;
        return this;
    }

    public void setBusinessPhoneNr(String businessPhoneNr) {
        this.businessPhoneNr = businessPhoneNr;
    }

    public String getPrivatePhoneNr() {
        return privatePhoneNr;
    }

    public Address privatePhoneNr(String privatePhoneNr) {
        this.privatePhoneNr = privatePhoneNr;
        return this;
    }

    public void setPrivatePhoneNr(String privatePhoneNr) {
        this.privatePhoneNr = privatePhoneNr;
    }

    public String getMobilePhoneNr() {
        return mobilePhoneNr;
    }

    public Address mobilePhoneNr(String mobilePhoneNr) {
        this.mobilePhoneNr = mobilePhoneNr;
        return this;
    }

    public void setMobilePhoneNr(String mobilePhoneNr) {
        this.mobilePhoneNr = mobilePhoneNr;
    }

    public WeddingGuest getGuestPrivate() {
        return guestPrivate;
    }

    public Address guestPrivate(WeddingGuest weddingGuest) {
        this.guestPrivate = weddingGuest;
        return this;
    }

    public void setGuestPrivate(WeddingGuest weddingGuest) {
        this.guestPrivate = weddingGuest;
    }

    public WeddingGuest getGuestBusiness() {
        return guestBusiness;
    }

    public Address guestBusiness(WeddingGuest weddingGuest) {
        this.guestBusiness = weddingGuest;
        return this;
    }

    public void setGuestBusiness(WeddingGuest weddingGuest) {
        this.guestBusiness = weddingGuest;
    }

    public WeddingService getServiceBusiness() {
        return serviceBusiness;
    }

    public Address serviceBusiness(WeddingService weddingService) {
        this.serviceBusiness = weddingService;
        return this;
    }

    public void setServiceBusiness(WeddingService weddingService) {
        this.serviceBusiness = weddingService;
    }

    public WeddingService getServicePrivate() {
        return servicePrivate;
    }

    public Address servicePrivate(WeddingService weddingService) {
        this.servicePrivate = weddingService;
        return this;
    }

    public void setServicePrivate(WeddingService weddingService) {
        this.servicePrivate = weddingService;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", addressType='" + getAddressType() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", businessPhoneNr='" + getBusinessPhoneNr() + "'" +
            ", privatePhoneNr='" + getPrivatePhoneNr() + "'" +
            ", mobilePhoneNr='" + getMobilePhoneNr() + "'" +
            "}";
    }
}
