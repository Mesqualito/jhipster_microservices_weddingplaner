package rocks.gebsattel.hochzeit.domain;

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
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    @Column(name = "message_short_description", length = 80, nullable = false)
    private String messageShortDescription;

    @Column(name = "message_init_time")
    private Instant messageInitTime;

    @Lob
    @Column(name = "message_text")
    private String messageText;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "message_valid_from")
    private LocalDate messageValidFrom;

    @Column(name = "message_valid_until")
    private LocalDate messageValidUntil;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    private AppUser owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "message_message_recipient",
               joinColumns = @JoinColumn(name="messages_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="message_recipients_id", referencedColumnName="id"))
    private Set<AppUser> messageRecipients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageShortDescription() {
        return messageShortDescription;
    }

    public Message messageShortDescription(String messageShortDescription) {
        this.messageShortDescription = messageShortDescription;
        return this;
    }

    public void setMessageShortDescription(String messageShortDescription) {
        this.messageShortDescription = messageShortDescription;
    }

    public Instant getMessageInitTime() {
        return messageInitTime;
    }

    public Message messageInitTime(Instant messageInitTime) {
        this.messageInitTime = messageInitTime;
        return this;
    }

    public void setMessageInitTime(Instant messageInitTime) {
        this.messageInitTime = messageInitTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public Message messageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Language getLanguage() {
        return language;
    }

    public Message language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LocalDate getMessageValidFrom() {
        return messageValidFrom;
    }

    public Message messageValidFrom(LocalDate messageValidFrom) {
        this.messageValidFrom = messageValidFrom;
        return this;
    }

    public void setMessageValidFrom(LocalDate messageValidFrom) {
        this.messageValidFrom = messageValidFrom;
    }

    public LocalDate getMessageValidUntil() {
        return messageValidUntil;
    }

    public Message messageValidUntil(LocalDate messageValidUntil) {
        this.messageValidUntil = messageValidUntil;
        return this;
    }

    public void setMessageValidUntil(LocalDate messageValidUntil) {
        this.messageValidUntil = messageValidUntil;
    }

    public Integer getWeight() {
        return weight;
    }

    public Message weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public AppUser getOwner() {
        return owner;
    }

    public Message owner(AppUser appUser) {
        this.owner = appUser;
        return this;
    }

    public void setOwner(AppUser appUser) {
        this.owner = appUser;
    }

    public Set<AppUser> getMessageRecipients() {
        return messageRecipients;
    }

    public Message messageRecipients(Set<AppUser> appUsers) {
        this.messageRecipients = appUsers;
        return this;
    }

    public Message addMessageRecipient(AppUser appUser) {
        this.messageRecipients.add(appUser);
        appUser.getReceivedMessages().add(this);
        return this;
    }

    public Message removeMessageRecipient(AppUser appUser) {
        this.messageRecipients.remove(appUser);
        appUser.getReceivedMessages().remove(this);
        return this;
    }

    public void setMessageRecipients(Set<AppUser> appUsers) {
        this.messageRecipients = appUsers;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", messageShortDescription='" + getMessageShortDescription() + "'" +
            ", messageInitTime='" + getMessageInitTime() + "'" +
            ", messageText='" + getMessageText() + "'" +
            ", language='" + getLanguage() + "'" +
            ", messageValidFrom='" + getMessageValidFrom() + "'" +
            ", messageValidUntil='" + getMessageValidUntil() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
