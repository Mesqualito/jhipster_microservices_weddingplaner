package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.AddressService;
import rocks.gebsattel.hochzeit.domain.Address;
import rocks.gebsattel.hochzeit.repository.AddressRepository;
import rocks.gebsattel.hochzeit.repository.search.AddressSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.AddressDTO;
import rocks.gebsattel.hochzeit.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Address.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    private final AddressSearchRepository addressSearchRepository;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper, AddressSearchRepository addressSearchRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.addressSearchRepository = addressSearchRepository;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        AddressDTO result = addressMapper.toDto(address);
        addressSearchRepository.save(address);
        return result;
    }

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll(pageable)
            .map(addressMapper::toDto);
    }


    /**
     *  get all the addresses where GuestPrivate is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AddressDTO> findAllWhereGuestPrivateIsNull() {
        log.debug("Request to get all addresses where GuestPrivate is null");
        return StreamSupport
            .stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getGuestPrivate() == null)
            .map(addressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the addresses where GuestBusiness is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AddressDTO> findAllWhereGuestBusinessIsNull() {
        log.debug("Request to get all addresses where GuestBusiness is null");
        return StreamSupport
            .stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getGuestBusiness() == null)
            .map(addressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the addresses where ServiceBusiness is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AddressDTO> findAllWhereServiceBusinessIsNull() {
        log.debug("Request to get all addresses where ServiceBusiness is null");
        return StreamSupport
            .stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getServiceBusiness() == null)
            .map(addressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the addresses where ServicePrivate is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AddressDTO> findAllWhereServicePrivateIsNull() {
        log.debug("Request to get all addresses where ServicePrivate is null");
        return StreamSupport
            .stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getServicePrivate() == null)
            .map(addressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one address by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AddressDTO findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        return addressMapper.toDto(address);
    }

    /**
     * Delete the address by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
        addressSearchRepository.delete(id);
    }

    /**
     * Search for the address corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Addresses for query {}", query);
        Page<Address> result = addressSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(addressMapper::toDto);
    }
}
