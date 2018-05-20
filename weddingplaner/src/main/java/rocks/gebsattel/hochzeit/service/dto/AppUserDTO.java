package rocks.gebsattel.hochzeit.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import rocks.gebsattel.hochzeit.domain.enumeration.Language;

/**
 * A DTO for the AppUser entity.
 */
public class AppUserDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String appUserAccount;

    @Size(min = 8, max = 15)
    private String appUserPassword;

    private Language language;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppUserAccount() {
        return appUserAccount;
    }

    public void setAppUserAccount(String appUserAccount) {
        this.appUserAccount = appUserAccount;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if(appUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", appUserAccount='" + getAppUserAccount() + "'" +
            ", appUserPassword='" + getAppUserPassword() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
