package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.WeddingGuestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WeddingGuest.
 */
public interface WeddingGuestService {

    /**
     * Save a weddingGuest.
     *
     * @param weddingGuestDTO the entity to save
     * @return the persisted entity
     */
    WeddingGuestDTO save(WeddingGuestDTO weddingGuestDTO);

    /**
     * Get all the weddingGuests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WeddingGuestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" weddingGuest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WeddingGuestDTO findOne(Long id);

    /**
     * Delete the "id" weddingGuest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the weddingGuest corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WeddingGuestDTO> search(String query, Pageable pageable);
}
