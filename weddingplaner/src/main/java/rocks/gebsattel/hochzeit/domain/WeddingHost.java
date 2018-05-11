package rocks.gebsattel.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WeddingHost.
 */
@Entity
@Table(name = "wedding_host")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeddingHost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 80)
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

    @OneToOne
    @JoinColumn(unique = true)
    private AppUser appUser;

    @OneToOne
    @JoinColumn(unique = true)
    private PartyFood foodProposalAcceptHost;

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

    public WeddingHost firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public WeddingHost lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public WeddingHost email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public WeddingHost appUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public PartyFood getFoodProposalAcceptHost() {
        return foodProposalAcceptHost;
    }

    public WeddingHost foodProposalAcceptHost(PartyFood partyFood) {
        this.foodProposalAcceptHost = partyFood;
        return this;
    }

    public void setFoodProposalAcceptHost(PartyFood partyFood) {
        this.foodProposalAcceptHost = partyFood;
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
        WeddingHost weddingHost = (WeddingHost) o;
        if (weddingHost.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weddingHost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeddingHost{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
