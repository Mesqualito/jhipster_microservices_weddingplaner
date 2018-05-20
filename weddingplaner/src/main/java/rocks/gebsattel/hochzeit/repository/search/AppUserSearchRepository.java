package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.AppUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AppUser entity.
 */
public interface AppUserSearchRepository extends ElasticsearchRepository<AppUser, Long> {
}
