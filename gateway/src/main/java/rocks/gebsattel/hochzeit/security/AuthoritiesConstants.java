package rocks.gebsattel.hochzeit.security;


// for individual Per-User-ROLES see:
// - https://stackoverflow.com/questions/50416007/how-to-create-a-new-authority-in-jhipster/50433463#50433463
// - https://stackoverflow.com/questions/50085892/jhipster-decode-jwt-token-access-to-roles
// - https://stackoverflow.com/questions/50416007/how-to-create-a-new-authority-in-jhipster
// - https://stackoverflow.com/questions/50434004/jhipster-individual-user-roles-for-personal-data-and-post-authorisation

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
