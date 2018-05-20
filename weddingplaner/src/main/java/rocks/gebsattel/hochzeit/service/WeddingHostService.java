package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.WeddingHostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WeddingHost.
 */
public interface WeddingHostService {

    /**
     * Save a weddingHost.
     *
     * @param weddingHostDTO the entity to save
     * @return the persisted entity
     */
    WeddingHostDTO save(WeddingHostDTO weddingHostDTO);

    /**
     * Get all the weddingHosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WeddingHostDTO> findAll(Pageable pageable);

    /**
     * Get the "id" weddingHost.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WeddingHostDTO findOne(Long id);

    /**
     * Delete the "id" weddingHost.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the weddingHost corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WeddingHostDTO> search(String query, Pageable pageable);
}
