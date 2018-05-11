package rocks.gebsattel.hochzeit.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WeddingService entity.
 */
public class WeddingServiceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 80)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 80)
    private String lastName;

    @NotNull
    @Size(min = 6, max = 120)
    private String email;

    private Instant serviceCommittedDate;

    private Long businessAddressId;

    private Long privateAddressId;

    private Long appUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getServiceCommittedDate() {
        return serviceCommittedDate;
    }

    public void setServiceCommittedDate(Instant serviceCommittedDate) {
        this.serviceCommittedDate = serviceCommittedDate;
    }

    public Long getBusinessAddressId() {
        return businessAddressId;
    }

    public void setBusinessAddressId(Long addressId) {
        this.businessAddressId = addressId;
    }

    public Long getPrivateAddressId() {
        return privateAddressId;
    }

    public void setPrivateAddressId(Long addressId) {
        this.privateAddressId = addressId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeddingServiceDTO weddingServiceDTO = (WeddingServiceDTO) o;
        if(weddingServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weddingServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeddingServiceDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", serviceCommittedDate='" + getServiceCommittedDate() + "'" +
            "}";
    }
}
