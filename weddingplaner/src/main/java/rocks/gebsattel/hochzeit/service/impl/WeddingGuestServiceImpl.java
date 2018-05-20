package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.WeddingGuestService;
import rocks.gebsattel.hochzeit.domain.WeddingGuest;
import rocks.gebsattel.hochzeit.repository.WeddingGuestRepository;
import rocks.gebsattel.hochzeit.repository.search.WeddingGuestSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.WeddingGuestDTO;
import rocks.gebsattel.hochzeit.service.mapper.WeddingGuestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WeddingGuest.
 */
@Service
@Transactional
public class WeddingGuestServiceImpl implements WeddingGuestService {

    private final Logger log = LoggerFactory.getLogger(WeddingGuestServiceImpl.class);

    private final WeddingGuestRepository weddingGuestRepository;

    private final WeddingGuestMapper weddingGuestMapper;

    private final WeddingGuestSearchRepository weddingGuestSearchRepository;

    public WeddingGuestServiceImpl(WeddingGuestRepository weddingGuestRepository, WeddingGuestMapper weddingGuestMapper, WeddingGuestSearchRepository weddingGuestSearchRepository) {
        this.weddingGuestRepository = weddingGuestRepository;
        this.weddingGuestMapper = weddingGuestMapper;
        this.weddingGuestSearchRepository = weddingGuestSearchRepository;
    }

    /**
     * Save a weddingGuest.
     *
     * @param weddingGuestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WeddingGuestDTO save(WeddingGuestDTO weddingGuestDTO) {
        log.debug("Request to save WeddingGuest : {}", weddingGuestDTO);
        WeddingGuest weddingGuest = weddingGuestMapper.toEntity(weddingGuestDTO);
        weddingGuest = weddingGuestRepository.save(weddingGuest);
        WeddingGuestDTO result = weddingGuestMapper.toDto(weddingGuest);
        weddingGuestSearchRepository.save(weddingGuest);
        return result;
    }

    /**
     * Get all the weddingGuests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeddingGuestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeddingGuests");
        return weddingGuestRepository.findAll(pageable)
            .map(weddingGuestMapper::toDto);
    }

    /**
     * Get one weddingGuest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WeddingGuestDTO findOne(Long id) {
        log.debug("Request to get WeddingGuest : {}", id);
        WeddingGuest weddingGuest = weddingGuestRepository.findOne(id);
        return weddingGuestMapper.toDto(weddingGuest);
    }

    /**
     * Delete the weddingGuest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeddingGuest : {}", id);
        weddingGuestRepository.delete(id);
        weddingGuestSearchRepository.delete(id);
    }

    /**
     * Search for the weddingGuest corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeddingGuestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WeddingGuests for query {}", query);
        Page<WeddingGuest> result = weddingGuestSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(weddingGuestMapper::toDto);
    }
}
