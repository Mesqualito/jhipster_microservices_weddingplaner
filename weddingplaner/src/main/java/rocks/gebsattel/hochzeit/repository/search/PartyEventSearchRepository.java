package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.PartyEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PartyEvent entity.
 */
public interface PartyEventSearchRepository extends ElasticsearchRepository<PartyEvent, Long> {
}
