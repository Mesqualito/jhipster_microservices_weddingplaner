package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.AppUserDTO;
import java.util.List;

/**
 * Service Interface for managing AppUser.
 */
public interface AppUserService {

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save
     * @return the persisted entity
     */
    AppUserDTO save(AppUserDTO appUserDTO);

    /**
     * Get all the appUsers.
     *
     * @return the list of entities
     */
    List<AppUserDTO> findAll();
    /**
     * Get all the AppUserDTO where WeddingGuest is null.
     *
     * @return the list of entities
     */
    List<AppUserDTO> findAllWhereWeddingGuestIsNull();
    /**
     * Get all the AppUserDTO where WeddingHost is null.
     *
     * @return the list of entities
     */
    List<AppUserDTO> findAllWhereWeddingHostIsNull();
    /**
     * Get all the AppUserDTO where WeddingService is null.
     *
     * @return the list of entities
     */
    List<AppUserDTO> findAllWhereWeddingServiceIsNull();

    /**
     * Get the "id" appUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AppUserDTO findOne(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
