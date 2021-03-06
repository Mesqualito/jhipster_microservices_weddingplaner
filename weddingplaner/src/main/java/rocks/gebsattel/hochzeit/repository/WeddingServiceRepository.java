package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.WeddingService;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WeddingService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeddingServiceRepository extends JpaRepository<WeddingService, Long> {

}
