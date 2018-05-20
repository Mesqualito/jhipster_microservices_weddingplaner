package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.PartyEventService;
import rocks.gebsattel.hochzeit.domain.PartyEvent;
import rocks.gebsattel.hochzeit.repository.PartyEventRepository;
import rocks.gebsattel.hochzeit.repository.search.PartyEventSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.PartyEventDTO;
import rocks.gebsattel.hochzeit.service.mapper.PartyEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PartyEvent.
 */
@Service
@Transactional
public class PartyEventServiceImpl implements PartyEventService {

    private final Logger log = LoggerFactory.getLogger(PartyEventServiceImpl.class);

    private final PartyEventRepository partyEventRepository;

    private final PartyEventMapper partyEventMapper;

    private final PartyEventSearchRepository partyEventSearchRepository;

    public PartyEventServiceImpl(PartyEventRepository partyEventRepository, PartyEventMapper partyEventMapper, PartyEventSearchRepository partyEventSearchRepository) {
        this.partyEventRepository = partyEventRepository;
        this.partyEventMapper = partyEventMapper;
        this.partyEventSearchRepository = partyEventSearchRepository;
    }

    /**
     * Save a partyEvent.
     *
     * @param partyEventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PartyEventDTO save(PartyEventDTO partyEventDTO) {
        log.debug("Request to save PartyEvent : {}", partyEventDTO);
        PartyEvent partyEvent = partyEventMapper.toEntity(partyEventDTO);
        partyEvent = partyEventRepository.save(partyEvent);
        PartyEventDTO result = partyEventMapper.toDto(partyEvent);
        partyEventSearchRepository.save(partyEvent);
        return result;
    }

    /**
     * Get all the partyEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartyEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PartyEvents");
        return partyEventRepository.findAll(pageable)
            .map(partyEventMapper::toDto);
    }

    /**
     * Get one partyEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PartyEventDTO findOne(Long id) {
        log.debug("Request to get PartyEvent : {}", id);
        PartyEvent partyEvent = partyEventRepository.findOne(id);
        return partyEventMapper.toDto(partyEvent);
    }

    /**
     * Delete the partyEvent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyEvent : {}", id);
        partyEventRepository.delete(id);
        partyEventSearchRepository.delete(id);
    }

    /**
     * Search for the partyEvent corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartyEventDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PartyEvents for query {}", query);
        Page<PartyEvent> result = partyEventSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(partyEventMapper::toDto);
    }
}
