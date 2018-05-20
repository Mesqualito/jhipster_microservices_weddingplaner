package rocks.gebsattel.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import rocks.gebsattel.hochzeit.domain.enumeration.Language;

/**
 * A PartyEvent.
 */
@Entity
@Table(name = "party_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "partyevent")
public class PartyEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    @Column(name = "event_name", length = 80, nullable = false)
    private String eventName;

    @Size(min = 20, max = 1024)
    @Column(name = "event_short_description", length = 1024)
    private String eventShortDescription;

    @Lob
    @Column(name = "event_long_description")
    private String eventLongDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "event_init_time")
    private Instant eventInitTime;

    @Column(name = "event_start_time")
    private LocalDate eventStartTime;

    @Column(name = "event_end_time")
    private LocalDate eventEndTime;

    @Column(name = "event_max_person")
    private Integer eventMaxPerson;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    private AppUser owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public PartyEvent eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventShortDescription() {
        return eventShortDescription;
    }

    public PartyEvent eventShortDescription(String eventShortDescription) {
        this.eventShortDescription = eventShortDescription;
        return this;
    }

    public void setEventShortDescription(String eventShortDescription) {
        this.eventShortDescription = eventShortDescription;
    }

    public String getEventLongDescription() {
        return eventLongDescription;
    }

    public PartyEvent eventLongDescription(String eventLongDescription) {
        this.eventLongDescription = eventLongDescription;
        return this;
    }

    public void setEventLongDescription(String eventLongDescription) {
        this.eventLongDescription = eventLongDescription;
    }

    public Language getLanguage() {
        return language;
    }

    public PartyEvent language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Instant getEventInitTime() {
        return eventInitTime;
    }

    public PartyEvent eventInitTime(Instant eventInitTime) {
        this.eventInitTime = eventInitTime;
        return this;
    }

    public void setEventInitTime(Instant eventInitTime) {
        this.eventInitTime = eventInitTime;
    }

    public LocalDate getEventStartTime() {
        return eventStartTime;
    }

    public PartyEvent eventStartTime(LocalDate eventStartTime) {
        this.eventStartTime = eventStartTime;
        return this;
    }

    public void setEventStartTime(LocalDate eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalDate getEventEndTime() {
        return eventEndTime;
    }

    public PartyEvent eventEndTime(LocalDate eventEndTime) {
        this.eventEndTime = eventEndTime;
        return this;
    }

    public void setEventEndTime(LocalDate eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Integer getEventMaxPerson() {
        return eventMaxPerson;
    }

    public PartyEvent eventMaxPerson(Integer eventMaxPerson) {
        this.eventMaxPerson = eventMaxPerson;
        return this;
    }

    public void setEventMaxPerson(Integer eventMaxPerson) {
        this.eventMaxPerson = eventMaxPerson;
    }

    public Integer getWeight() {
        return weight;
    }

    public PartyEvent weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public AppUser getOwner() {
        return owner;
    }

    public PartyEvent owner(AppUser appUser) {
        this.owner = appUser;
        return this;
    }

    public void setOwner(AppUser appUser) {
        this.owner = appUser;
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
        PartyEvent partyEvent = (PartyEvent) o;
        if (partyEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyEvent{" +
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
