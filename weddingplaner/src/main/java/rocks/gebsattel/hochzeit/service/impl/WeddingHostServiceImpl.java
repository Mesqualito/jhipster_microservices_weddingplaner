package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.WeddingHostService;
import rocks.gebsattel.hochzeit.domain.WeddingHost;
import rocks.gebsattel.hochzeit.repository.WeddingHostRepository;
import rocks.gebsattel.hochzeit.service.dto.WeddingHostDTO;
import rocks.gebsattel.hochzeit.service.mapper.WeddingHostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WeddingHost.
 */
@Service
@Transactional
public class WeddingHostServiceImpl implements WeddingHostService {

    private final Logger log = LoggerFactory.getLogger(WeddingHostServiceImpl.class);

    private final WeddingHostRepository weddingHostRepository;

    private final WeddingHostMapper weddingHostMapper;

    public WeddingHostServiceImpl(WeddingHostRepository weddingHostRepository, WeddingHostMapper weddingHostMapper) {
        this.weddingHostRepository = weddingHostRepository;
        this.weddingHostMapper = weddingHostMapper;
    }

    /**
     * Save a weddingHost.
     *
     * @param weddingHostDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WeddingHostDTO save(WeddingHostDTO weddingHostDTO) {
        log.debug("Request to save WeddingHost : {}", weddingHostDTO);
        WeddingHost weddingHost = weddingHostMapper.toEntity(weddingHostDTO);
        weddingHost = weddingHostRepository.save(weddingHost);
        return weddingHostMapper.toDto(weddingHost);
    }

    /**
     * Get all the weddingHosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeddingHostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WeddingHosts");
        return weddingHostRepository.findAll(pageable)
            .map(weddingHostMapper::toDto);
    }

    /**
     * Get one weddingHost by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WeddingHostDTO findOne(Long id) {
        log.debug("Request to get WeddingHost : {}", id);
        WeddingHost weddingHost = weddingHostRepository.findOne(id);
        return weddingHostMapper.toDto(weddingHost);
    }

    /**
     * Delete the weddingHost by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeddingHost : {}", id);
        weddingHostRepository.delete(id);
    }
}
