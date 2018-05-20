package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.PartyEvent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PartyEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyEventRepository extends JpaRepository<PartyEvent, Long> {

}
