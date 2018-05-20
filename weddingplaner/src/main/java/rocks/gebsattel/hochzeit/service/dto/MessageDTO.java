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
 * A DTO for the Message entity.
 */
public class MessageDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6, max = 80)
    private String messageShortDescription;

    private Instant messageInitTime;

    @Lob
    private String messageText;

    private Language language;

    private LocalDate messageValidFrom;

    private LocalDate messageValidUntil;

    private Integer weight;

    private Long ownerId;

    private Set<AppUserDTO> messageRecipients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageShortDescription() {
        return messageShortDescription;
    }

    public void setMessageShortDescription(String messageShortDescription) {
        this.messageShortDescription = messageShortDescription;
    }

    public Instant getMessageInitTime() {
        return messageInitTime;
    }

    public void setMessageInitTime(Instant messageInitTime) {
        this.messageInitTime = messageInitTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LocalDate getMessageValidFrom() {
        return messageValidFrom;
    }

    public void setMessageValidFrom(LocalDate messageValidFrom) {
        this.messageValidFrom = messageValidFrom;
    }

    public LocalDate getMessageValidUntil() {
        return messageValidUntil;
    }

    public void setMessageValidUntil(LocalDate messageValidUntil) {
        this.messageValidUntil = messageValidUntil;
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

    public Set<AppUserDTO> getMessageRecipients() {
        return messageRecipients;
    }

    public void setMessageRecipients(Set<AppUserDTO> appUsers) {
        this.messageRecipients = appUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if(messageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
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
