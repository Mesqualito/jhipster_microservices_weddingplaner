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
 * A DTO for the PartyEvent entity.
 */
public class PartyEventDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    private String eventName;

    @Size(min = 20, max = 1024)
    private String eventShortDescription;

    @Lob
    private String eventLongDescription;

    private Language language;

    private Instant eventInitTime;

    private LocalDate eventStartTime;

    private LocalDate eventEndTime;

    private Integer eventMaxPerson;

    private Integer weight;

    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventShortDescription() {
        return eventShortDescription;
    }

    public void setEventShortDescription(String eventShortDescription) {
        this.eventShortDescription = eventShortDescription;
    }

    public String getEventLongDescription() {
        return eventLongDescription;
    }

    public void setEventLongDescription(String eventLongDescription) {
        this.eventLongDescription = eventLongDescription;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Instant getEventInitTime() {
        return eventInitTime;
    }

    public void setEventInitTime(Instant eventInitTime) {
        this.eventInitTime = eventInitTime;
    }

    public LocalDate getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(LocalDate eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalDate getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(LocalDate eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Integer getEventMaxPerson() {
        return eventMaxPerson;
    }

    public void setEventMaxPerson(Integer eventMaxPerson) {
        this.eventMaxPerson = eventMaxPerson;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartyEventDTO partyEventDTO = (PartyEventDTO) o;
        if(partyEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyEventDTO{" +
            "id=" + getId() +
            ", eventName='" + getEventName() + "'" +
            ", eventShortDescription='" + getEventShortDescription() + "'" +
            ", eventLongDescription='" + getEventLongDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            ", eventInitTime='" + getEventInitTime() + "'" +
            ", eventStartTime='" + getEventStartTime() + "'" +
            ", eventEndTime='" + getEventEndTime() + "'" +
            ", eventMaxPerson=" + getEventMaxPerson() +
            ", weight=" + getWeight() +
            "}";
    }
}
