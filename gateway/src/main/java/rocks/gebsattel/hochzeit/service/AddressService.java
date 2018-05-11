package rocks.gebsattel.hochzeit.service;

import rocks.gebsattel.hochzeit.service.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    AddressDTO save(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AddressDTO> findAll(Pageable pageable);
    /**
     * Get all the AddressDTO where GuestPrivate is null.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAllWhereGuestPrivateIsNull();
    /**
     * Get all the AddressDTO where GuestBusiness is null.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAllWhereGuestBusinessIsNull();
    /**
     * Get all the AddressDTO where ServiceBusiness is null.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAllWhereServiceBusinessIsNull();
    /**
     * Get all the AddressDTO where ServicePrivate is null.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAllWhereServicePrivateIsNull();

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AddressDTO findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
