package rocks.gebsattel.hochzeit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import rocks.gebsattel.hochzeit.domain.enumeration.Language;

/**
 * A PartyFood.
 */
@Entity
@Table(name = "party_food")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "partyfood")
public class PartyFood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    @Column(name = "food_name", length = 80, nullable = false)
    private String foodName;

    @Size(min = 20, max = 1024)
    @Column(name = "food_short_description", length = 1024)
    private String foodShortDescription;

    @Lob
    @Column(name = "food_long_description")
    private String foodLongDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "food_quantity_persons")
    private Integer foodQuantityPersons;

    @Column(name = "food_best_serve_time")
    private LocalDate foodBestServeTime;

    @Column(name = "food_proposal_accepted")
    private Boolean foodProposalAccepted;

    @Column(name = "food_proposal_accept_time")
    private Instant foodProposalAcceptTime;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    private AppUser owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "party_food_member_user",
               joinColumns = @JoinColumn(name="party_foods_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="member_users_id", referencedColumnName="id"))
    private Set<AppUser> memberUsers = new HashSet<>();

    @OneToOne(mappedBy = "foodProposalAcceptHost")
    @JsonIgnore
    private WeddingHost acceptedByHost;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public PartyFood foodName(String foodName) {
        this.foodName = foodName;
        return this;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodShortDescription() {
        return foodShortDescription;
    }

    public PartyFood foodShortDescription(String foodShortDescription) {
        this.foodShortDescription = foodShortDescription;
        return this;
    }

    public void setFoodShortDescription(String foodShortDescription) {
        this.foodShortDescription = foodShortDescription;
    }

    public String getFoodLongDescription() {
        return foodLongDescription;
    }

    public PartyFood foodLongDescription(String foodLongDescription) {
        this.foodLongDescription = foodLongDescription;
        return this;
    }

    public void setFoodLongDescription(String foodLongDescription) {
        this.foodLongDescription = foodLongDescription;
    }

    public Language getLanguage() {
        return language;
    }

    public PartyFood language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getFoodQuantityPersons() {
        return foodQuantityPersons;
    }

    public PartyFood foodQuantityPersons(Integer foodQuantityPersons) {
        this.foodQuantityPersons = foodQuantityPersons;
        return this;
    }

    public void setFoodQuantityPersons(Integer foodQuantityPersons) {
        this.foodQuantityPersons = foodQuantityPersons;
    }

    public LocalDate getFoodBestServeTime() {
        return foodBestServeTime;
    }

    public PartyFood foodBestServeTime(LocalDate foodBestServeTime) {
        this.foodBestServeTime = foodBestServeTime;
        return this;
    }

    public void setFoodBestServeTime(LocalDate foodBestServeTime) {
        this.foodBestServeTime = foodBestServeTime;
    }

    public Boolean isFoodProposalAccepted() {
        return foodProposalAccepted;
    }

    public PartyFood foodProposalAccepted(Boolean foodProposalAccepted) {
        this.foodProposalAccepted = foodProposalAccepted;
        return this;
    }

    public void setFoodProposalAccepted(Boolean foodProposalAccepted) {
        this.foodProposalAccepted = foodProposalAccepted;
    }

    public Instant getFoodProposalAcceptTime() {
        return foodProposalAcceptTime;
    }

    public PartyFood foodProposalAcceptTime(Instant foodProposalAcceptTime) {
        this.foodProposalAcceptTime = foodProposalAcceptTime;
        return this;
    }

    public void setFoodProposalAcceptTime(Instant foodProposalAcceptTime) {
        this.foodProposalAcceptTime = foodProposalAcceptTime;
    }

    public Integer getWeight() {
        return weight;
    }

    public PartyFood weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public AppUser getOwner() {
        return owner;
    }

    public PartyFood owner(AppUser appUser) {
        this.owner = appUser;
        return this;
    }

    public void setOwner(AppUser appUser) {
        this.owner = appUser;
    }

    public Set<AppUser> getMemberUsers() {
        return memberUsers;
    }

    public PartyFood memberUsers(Set<AppUser> appUsers) {
        this.memberUsers = appUsers;
        return this;
    }

    public PartyFood addMemberUser(AppUser appUser) {
        this.memberUsers.add(appUser);
        appUser.getFoodMemberUsers().add(this);
        return this;
    }

    public PartyFood removeMemberUser(AppUser appUser) {
        this.memberUsers.remove(appUser);
        appUser.getFoodMemberUsers().remove(this);
        return this;
    }

    public void setMemberUsers(Set<AppUser> appUsers) {
        this.memberUsers = appUsers;
    }

    public WeddingHost getAcceptedByHost() {
        return acceptedByHost;
    }

    public PartyFood acceptedByHost(WeddingHost weddingHost) {
        this.acceptedByHost = weddingHost;
        return this;
    }

    public void setAcceptedByHost(WeddingHost weddingHost) {
        this.acceptedByHost = weddingHost;
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
        PartyFood partyFood = (PartyFood) o;
        if (partyFood.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyFood.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyFood{" +
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
