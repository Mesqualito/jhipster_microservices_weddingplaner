package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.PartyFoodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PartyFood.
 */
public interface PartyFoodService {

    /**
     * Save a partyFood.
     *
     * @param partyFoodDTO the entity to save
     * @return the persisted entity
     */
    PartyFoodDTO save(PartyFoodDTO partyFoodDTO);

    /**
     * Get all the partyFoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PartyFoodDTO> findAll(Pageable pageable);
    /**
     * Get all the PartyFoodDTO where AcceptedByHost is null.
     *
     * @return the list of entities
     */
    List<PartyFoodDTO> findAllWhereAcceptedByHostIsNull();

    /**
     * Get the "id" partyFood.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PartyFoodDTO findOne(Long id);

    /**
     * Delete the "id" partyFood.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
