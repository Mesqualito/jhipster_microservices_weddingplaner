package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.PartyEventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PartyEvent.
 */
public interface PartyEventService {

    /**
     * Save a partyEvent.
     *
     * @param partyEventDTO the entity to save
     * @return the persisted entity
     */
    PartyEventDTO save(PartyEventDTO partyEventDTO);

    /**
     * Get all the partyEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PartyEventDTO> findAll(Pageable pageable);

    /**
     * Get the "id" partyEvent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PartyEventDTO findOne(Long id);

    /**
     * Delete the "id" partyEvent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the partyEvent corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PartyEventDTO> search(String query, Pageable pageable);
}
