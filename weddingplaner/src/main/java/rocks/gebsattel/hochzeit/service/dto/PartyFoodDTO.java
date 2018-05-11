package rocks.gebsattel.hochzeit.service.dto;


import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import rocks.gebsattel.hochzeit.domain.enumeration.Language;

/**
 * A DTO for the PartyFood entity.
 */
public class PartyFoodDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    private String foodName;

    @Size(min = 20, max = 1024)
    private String foodShortDescription;

    @Lob
    private String foodLongDescription;

    private Language language;

    private Integer foodQuantityPersons;

    private LocalDate foodBestServeTime;

    private Boolean foodProposalAccepted;

    private Instant foodProposalAcceptTime;

    private Integer weight;

    private Long ownerId;

    private Set<AppUserDTO> memberUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodShortDescription() {
        return foodShortDescription;
    }

    public void setFoodShortDescription(String foodShortDescription) {
        this.foodShortDescription = foodShortDescription;
    }

    public String getFoodLongDescription() {
        return foodLongDescription;
    }

    public void setFoodLongDescription(String foodLongDescription) {
        this.foodLongDescription = foodLongDescription;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getFoodQuantityPersons() {
        return foodQuantityPersons;
    }

    public void setFoodQuantityPersons(Integer foodQuantityPersons) {
        this.foodQuantityPersons = foodQuantityPersons;
    }

    public LocalDate getFoodBestServeTime() {
        return foodBestServeTime;
    }

    public void setFoodBestServeTime(LocalDate foodBestServeTime) {
        this.foodBestServeTime = foodBestServeTime;
    }

    public Boolean isFoodProposalAccepted() {
        return foodProposalAccepted;
    }

    public void setFoodProposalAccepted(Boolean foodProposalAccepted) {
        this.foodProposalAccepted = foodProposalAccepted;
    }

    public Instant getFoodProposalAcceptTime() {
        return foodProposalAcceptTime;
    }

    public void setFoodProposalAcceptTime(Instant foodProposalAcceptTime) {
        this.foodProposalAcceptTime = foodProposalAcceptTime;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long appUserId) {
        this.ownerId = appUserId;
    }

    public Set<AppUserDTO> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(Set<AppUserDTO> appUsers) {
        this.memberUsers = appUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyFoodDTO partyFoodDTO = (PartyFoodDTO) o;
        if(partyFoodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyFoodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyFoodDTO{" +
            "id=" + getId() +
            ", foodName='" + getFoodName() + "'" +
            ", foodShortDescription='" + getFoodShortDescription() + "'" +
            ", foodLongDescription='" + getFoodLongDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            ", foodQuantityPersons=" + getFoodQuantityPersons() +
            ", foodBestServeTime='" + getFoodBestServeTime() + "'" +
            ", foodProposalAccepted='" + isFoodProposalAccepted() + "'" +
            ", foodProposalAcceptTime='" + getFoodProposalAcceptTime() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
