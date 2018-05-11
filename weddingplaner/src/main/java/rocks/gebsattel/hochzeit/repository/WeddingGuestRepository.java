package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.WeddingGuest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WeddingGuest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeddingGuestRepository extends JpaRepository<WeddingGuest, Long> {

}
