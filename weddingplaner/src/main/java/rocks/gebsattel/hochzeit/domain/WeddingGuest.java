package rocks.gebsattel.hochzeit.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * The WeddingGuest entity.
 */
@ApiModel(description = "The WeddingGuest entity.")
@Entity
@Table(name = "wedding_guest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "weddingguest")
public class WeddingGuest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @Size(min = 2, max = 80)
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "first_name", length = 80, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 80)
    @Column(name = "last_name", length = 80, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 6, max = 120)
    @Column(name = "email", length = 120, nullable = false)
    private String email;

    @Column(name = "guest_invitation_date")
    private Instant guestInvitationDate;

    @Column(name = "guest_committed")
    private Boolean guestCommitted;

    @Column(name = "guest_rsvp_date")
    private Instant guestRsvpDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Address privateAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Address businessAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public WeddingGuest firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public WeddingGuest lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public WeddingGuest email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getGuestInvitationDate() {
        return guestInvitationDate;
    }

    public WeddingGuest guestInvitationDate(Instant guestInvitationDate) {
        this.guestInvitationDate = guestInvitationDate;
        return this;
    }

    public void setGuestInvitationDate(Instant guestInvitationDate) {
        this.guestInvitationDate = guestInvitationDate;
    }

    public Boolean isGuestCommitted() {
        return guestCommitted;
    }

    public WeddingGuest guestCommitted(Boolean guestCommitted) {
        this.guestCommitted = guestCommitted;
        return this;
    }

    public void setGuestCommitted(Boolean guestCommitted) {
        this.guestCommitted = guestCommitted;
    }

    public Instant getGuestRsvpDate() {
        return guestRsvpDate;
    }

    public WeddingGuest guestRsvpDate(Instant guestRsvpDate) {
        this.guestRsvpDate = guestRsvpDate;
        return this;
    }

    public void setGuestRsvpDate(Instant guestRsvpDate) {
        this.guestRsvpDate = guestRsvpDate;
    }

    public Address getPrivateAddress() {
        return privateAddress;
    }

    public WeddingGuest privateAddress(Address address) {
        this.privateAddress = address;
        return this;
    }

    public void setPrivateAddress(Address address) {
        this.privateAddress = address;
    }

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public WeddingGuest businessAddress(Address address) {
        this.businessAddress = address;
        return this;
    }

    public void setBusinessAddress(Address address) {
        this.businessAddress = address;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public WeddingGuest appUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
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
        WeddingGuest weddingGuest = (WeddingGuest) o;
        if (weddingGuest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weddingGuest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeddingGuest{" +
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
