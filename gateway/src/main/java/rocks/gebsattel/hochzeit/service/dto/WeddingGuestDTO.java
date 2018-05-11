package rocks.gebsattel.hochzeit.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WeddingGuest entity.
 */
public class WeddingGuestDTO implements Serializable {

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

    private Instant guestInvitationDate;

    private Boolean guestCommitted;

    private Instant guestRsvpDate;

    private Long privateAddressId;

    private Long businessAddressId;

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

    public Instant getGuestInvitationDate() {
        return guestInvitationDate;
    }

    public void setGuestInvitationDate(Instant guestInvitationDate) {
        this.guestInvitationDate = guestInvitationDate;
    }

    public Boolean isGuestCommitted() {
        return guestCommitted;
    }

    public void setGuestCommitted(Boolean guestCommitted) {
        this.guestCommitted = guestCommitted;
    }

    public Instant getGuestRsvpDate() {
        return guestRsvpDate;
    }

    public void setGuestRsvpDate(Instant guestRsvpDate) {
        this.guestRsvpDate = guestRsvpDate;
    }

    public Long getPrivateAddressId() {
        return privateAddressId;
    }

    public void setPrivateAddressId(Long addressId) {
        this.privateAddressId = addressId;
    }

    public Long getBusinessAddressId() {
        return businessAddressId;
    }

    public void setBusinessAddressId(Long addressId) {
        this.businessAddressId = addressId;
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

        WeddingGuestDTO weddingGuestDTO = (WeddingGuestDTO) o;
        if(weddingGuestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weddingGuestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeddingGuestDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", guestInvitationDate='" + getGuestInvitationDate() + "'" +
            ", guestCommitted='" + isGuestCommitted() + "'" +
            ", guestRsvpDate='" + getGuestRsvpDate() + "'" +
            "}";
    }
}
