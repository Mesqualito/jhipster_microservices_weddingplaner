package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.WeddingHost;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WeddingHost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeddingHostRepository extends JpaRepository<WeddingHost, Long> {

}
