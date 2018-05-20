package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.WeddingServiceService;
import rocks.gebsattel.hochzeit.domain.WeddingService;
import rocks.gebsattel.hochzeit.repository.WeddingServiceRepository;
import rocks.gebsattel.hochzeit.repository.search.WeddingServiceSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.WeddingServiceDTO;
import rocks.gebsattel.hochzeit.service.mapper.WeddingServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WeddingService.
 */
@Service
@Transactional
public class WeddingServiceServiceImpl implements WeddingServiceService {

    private final Logger log = LoggerFactory.getLogger(WeddingServiceServiceImpl.class);

    private final WeddingServiceRepository weddingServiceRepository;

    private final WeddingServiceMapper weddingServiceMapper;

    private final WeddingServiceSearchRepository weddingServiceSearchRepository;

    public WeddingServiceServiceImpl(WeddingServiceRepository weddingServiceRepository, WeddingServiceMapper weddingServiceMapper, WeddingServiceSearchRepository weddingServiceSearchRepository) {
        this.weddingServiceRepository = weddingServiceRepository;
        this.weddingServiceMapper = weddingServiceMapper;
        this.weddingServiceSearchRepository = weddingServiceSearchRepository;
    }

    /**
     * Save a weddingService.
     *
     * @param weddingServiceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WeddingServiceDTO save(WeddingServiceDTO weddingServiceDTO) {
        log.debug("Request to save WeddingService : {}", weddingServiceDTO);
        WeddingService weddingService = weddingServiceMapper.toEntity(weddingServiceDTO);
        weddingService = weddingServiceRepository.save(weddingService);
        WeddingServiceDTO result = weddingServiceMapper.toDto(weddingService);
        weddingServiceSearchRepository.save(weddingService);
        return result;
    }

    /**
     * Get all the weddingServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeddingServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeddingServices");
        return weddingServiceRepository.findAll(pageable)
            .map(weddingServiceMapper::toDto);
    }

    /**
     * Get one weddingService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WeddingServiceDTO findOne(Long id) {
        log.debug("Request to get WeddingService : {}", id);
        WeddingService weddingService = weddingServiceRepository.findOne(id);
        return weddingServiceMapper.toDto(weddingService);
    }

    /**
     * Delete the weddingService by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeddingService : {}", id);
        weddingServiceRepository.delete(id);
        weddingServiceSearchRepository.delete(id);
    }

    /**
     * Search for the weddingService corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeddingServiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WeddingServices for query {}", query);
        Page<WeddingService> result = weddingServiceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(weddingServiceMapper::toDto);
    }
}
