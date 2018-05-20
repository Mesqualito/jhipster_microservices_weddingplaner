package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.WeddingService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WeddingService entity.
 */
public interface WeddingServiceSearchRepository extends ElasticsearchRepository<WeddingService, Long> {
}
