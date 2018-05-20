package rocks.gebsattel.hochzeit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import rocks.gebsattel.hochzeit.domain.enumeration.Language;

/**
 * 'User' is a predesigned special entity
 * and can not have additional attributes etc.
 */
@ApiModel(description = "'User' is a predesigned special entity and can not have additional attributes etc.")
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appuser")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

//    @OneToOne
//    @JoinColumn(unique = true)
//    private User user;

    @NotNull
    @Size(max = 20)
    @Column(name = "app_user_account", length = 20, nullable = false)
    private String appUserAccount;

    @Size(min = 8, max = 15)
    @Column(name = "app_user_password", length = 15)
    private String appUserPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @OneToOne(mappedBy = "appUser")
    @JsonIgnore
    private WeddingGuest weddingGuest;

    @OneToOne(mappedBy = "appUser")
    @JsonIgnore
    private WeddingHost weddingHost;

    @OneToOne(mappedBy = "appUser")
    @JsonIgnore
    private WeddingService weddingService;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyFood> foodOwners = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyEvent> eventOwners = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messageOwners = new HashSet<>();

    @ManyToMany(mappedBy = "memberUsers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartyFood> foodMemberUsers = new HashSet<>();

    @ManyToMany(mappedBy = "messageRecipients")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> receivedMessages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppUserAccount() {
        return appUserAccount;
    }

    public AppUser appUserAccount(String appUserAccount) {
        this.appUserAccount = appUserAccount;
        return this;
    }

    public void setAppUserAccount(String appUserAccount) {
        this.appUserAccount = appUserAccount;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public AppUser appUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
        return this;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
    }

    public Language getLanguage() {
        return language;
    }

    public AppUser language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public User getUser() {
        return user;
    }

    public AppUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WeddingGuest getWeddingGuest() {
        return weddingGuest;
    }

    public AppUser weddingGuest(WeddingGuest weddingGuest) {
        this.weddingGuest = weddingGuest;
        return this;
    }

    public void setWeddingGuest(WeddingGuest weddingGuest) {
        this.weddingGuest = weddingGuest;
    }

    public WeddingHost getWeddingHost() {
        return weddingHost;
    }

    public AppUser weddingHost(WeddingHost weddingHost) {
        this.weddingHost = weddingHost;
        return this;
    }

    public void setWeddingHost(WeddingHost weddingHost) {
        this.weddingHost = weddingHost;
    }

    public WeddingService getWeddingService() {
        return weddingService;
    }

    public AppUser weddingService(WeddingService weddingService) {
        this.weddingService = weddingService;
        return this;
    }

    public void setWeddingService(WeddingService weddingService) {
        this.weddingService = weddingService;
    }

    public Set<PartyFood> getFoodOwners() {
        return foodOwners;
    }

    public AppUser foodOwners(Set<PartyFood> partyFoods) {
        this.foodOwners = partyFoods;
        return this;
    }

    public AppUser addFoodOwner(PartyFood partyFood) {
        this.foodOwners.add(partyFood);
        partyFood.setOwner(this);
        return this;
    }

    public AppUser removeFoodOwner(PartyFood partyFood) {
        this.foodOwners.remove(partyFood);
        partyFood.setOwner(null);
        return this;
    }

    public void setFoodOwners(Set<PartyFood> partyFoods) {
        this.foodOwners = partyFoods;
    }

    public Set<PartyEvent> getEventOwners() {
        return eventOwners;
    }

    public AppUser eventOwners(Set<PartyEvent> partyEvents) {
        this.eventOwners = partyEvents;
        return this;
    }

    public AppUser addEventOwner(PartyEvent partyEvent) {
        this.eventOwners.add(partyEvent);
        partyEvent.setOwner(this);
        return this;
    }

    public AppUser removeEventOwner(PartyEvent partyEvent) {
        this.eventOwners.remove(partyEvent);
        partyEvent.setOwner(null);
        return this;
    }

    public void setEventOwners(Set<PartyEvent> partyEvents) {
        this.eventOwners = partyEvents;
    }

    public Set<Message> getMessageOwners() {
        return messageOwners;
    }

    public AppUser messageOwners(Set<Message> messages) {
        this.messageOwners = messages;
        return this;
    }

    public AppUser addMessageOwner(Message message) {
        this.messageOwners.add(message);
        message.setOwner(this);
        return this;
    }

    public AppUser removeMessageOwner(Message message) {
        this.messageOwners.remove(message);
        message.setOwner(null);
        return this;
    }

    public void setMessageOwners(Set<Message> messages) {
        this.messageOwners = messages;
    }

    public Set<PartyFood> getFoodMemberUsers() {
        return foodMemberUsers;
    }

    public AppUser foodMemberUsers(Set<PartyFood> partyFoods) {
        this.foodMemberUsers = partyFoods;
        return this;
    }

    public AppUser addFoodMemberUser(PartyFood partyFood) {
        this.foodMemberUsers.add(partyFood);
        partyFood.getMemberUsers().add(this);
        return this;
    }

    public AppUser removeFoodMemberUser(PartyFood partyFood) {
        this.foodMemberUsers.remove(partyFood);
        partyFood.getMemberUsers().remove(this);
        return this;
    }

    public void setFoodMemberUsers(Set<PartyFood> partyFoods) {
        this.foodMemberUsers = partyFoods;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public AppUser receivedMessages(Set<Message> messages) {
        this.receivedMessages = messages;
        return this;
    }

    public AppUser addReceivedMessage(Message message) {
        this.receivedMessages.add(message);
        message.getMessageRecipients().add(this);
        return this;
    }

    public AppUser removeReceivedMessage(Message message) {
        this.receivedMessages.remove(message);
        message.getMessageRecipients().remove(this);
        return this;
    }

    public void setReceivedMessages(Set<Message> messages) {
        this.receivedMessages = messages;
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
        AppUser appUser = (AppUser) o;
        if (appUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", appUserAccount='" + getAppUserAccount() + "'" +
            ", appUserPassword='" + getAppUserPassword() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
