package rocks.gebsattel.hochzeit.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WeddingHost entity.
 */
public class WeddingHostDTO implements Serializable {

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

    private Long appUserId;

    private Long foodProposalAcceptHostId;

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

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getFoodProposalAcceptHostId() {
        return foodProposalAcceptHostId;
    }

    public void setFoodProposalAcceptHostId(Long partyFoodId) {
        this.foodProposalAcceptHostId = partyFoodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeddingHostDTO weddingHostDTO = (WeddingHostDTO) o;
        if(weddingHostDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weddingHostDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeddingHostDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
