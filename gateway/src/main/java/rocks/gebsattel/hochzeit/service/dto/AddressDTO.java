package rocks.gebsattel.hochzeit.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import rocks.gebsattel.hochzeit.domain.enumeration.AddressType;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    private AddressType addressType;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String zipCode;

    private String state;

    private String country;

    private String businessPhoneNr;

    private String privatePhoneNr;

    private String mobilePhoneNr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessPhoneNr() {
        return businessPhoneNr;
    }

    public void setBusinessPhoneNr(String businessPhoneNr) {
        this.businessPhoneNr = businessPhoneNr;
    }

    public String getPrivatePhoneNr() {
        return privatePhoneNr;
    }

    public void setPrivatePhoneNr(String privatePhoneNr) {
        this.privatePhoneNr = privatePhoneNr;
    }

    public String getMobilePhoneNr() {
        return mobilePhoneNr;
    }

    public void setMobilePhoneNr(String mobilePhoneNr) {
        this.mobilePhoneNr = mobilePhoneNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if(addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
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
