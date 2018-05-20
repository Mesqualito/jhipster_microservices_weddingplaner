package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.WeddingGuest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WeddingGuest entity.
 */
public interface WeddingGuestSearchRepository extends ElasticsearchRepository<WeddingGuest, Long> {
}
