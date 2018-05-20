package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.WeddingHost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WeddingHost entity.
 */
public interface WeddingHostSearchRepository extends ElasticsearchRepository<WeddingHost, Long> {
}
