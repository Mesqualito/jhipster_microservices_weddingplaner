package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.WeddingServiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WeddingService.
 */
public interface WeddingServiceService {

    /**
     * Save a weddingService.
     *
     * @param weddingServiceDTO the entity to save
     * @return the persisted entity
     */
    WeddingServiceDTO save(WeddingServiceDTO weddingServiceDTO);

    /**
     * Get all the weddingServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WeddingServiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" weddingService.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WeddingServiceDTO findOne(Long id);

    /**
     * Delete the "id" weddingService.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
