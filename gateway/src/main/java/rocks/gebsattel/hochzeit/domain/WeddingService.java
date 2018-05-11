package rocks.gebsattel.hochzeit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A WeddingService.
 */
@Entity
@Table(name = "wedding_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeddingService implements Serializable {

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

    @Column(name = "service_committed_date")
    private Instant serviceCommittedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Address businessAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Address privateAddress;

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

    public WeddingService firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public WeddingService lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public WeddingService email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getServiceCommittedDate() {
        return serviceCommittedDate;
    }

    public WeddingService serviceCommittedDate(Instant serviceCommittedDate) {
        this.serviceCommittedDate = serviceCommittedDate;
        return this;
    }

    public void setServiceCommittedDate(Instant serviceCommittedDate) {
        this.serviceCommittedDate = serviceCommittedDate;
    }

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public WeddingService businessAddress(Address address) {
        this.businessAddress = address;
        return this;
    }

    public void setBusinessAddress(Address address) {
        this.businessAddress = address;
    }

    public Address getPrivateAddress() {
        return privateAddress;
    }

    public WeddingService privateAddress(Address address) {
        this.privateAddress = address;
        return this;
    }

    public void setPrivateAddress(Address address) {
        this.privateAddress = address;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public WeddingService appUser(AppUser appUser) {
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
        WeddingService weddingService = (WeddingService) o;
        if (weddingService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weddingService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeddingService{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", serviceCommittedDate='" + getServiceCommittedDate() + "'" +
            "}";
    }
}
