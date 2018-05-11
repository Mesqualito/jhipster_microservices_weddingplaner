package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.PartyFoodService;
import rocks.gebsattel.hochzeit.domain.PartyFood;
import rocks.gebsattel.hochzeit.repository.PartyFoodRepository;
import rocks.gebsattel.hochzeit.service.dto.PartyFoodDTO;
import rocks.gebsattel.hochzeit.service.mapper.PartyFoodMapper;
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

/**
 * Service Implementation for managing PartyFood.
 */
@Service
@Transactional
public class PartyFoodServiceImpl implements PartyFoodService {

    private final Logger log = LoggerFactory.getLogger(PartyFoodServiceImpl.class);

    private final PartyFoodRepository partyFoodRepository;

    private final PartyFoodMapper partyFoodMapper;

    public PartyFoodServiceImpl(PartyFoodRepository partyFoodRepository, PartyFoodMapper partyFoodMapper) {
        this.partyFoodRepository = partyFoodRepository;
        this.partyFoodMapper = partyFoodMapper;
    }

    /**
     * Save a partyFood.
     *
     * @param partyFoodDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PartyFoodDTO save(PartyFoodDTO partyFoodDTO) {
        log.debug("Request to save PartyFood : {}", partyFoodDTO);
        PartyFood partyFood = partyFoodMapper.toEntity(partyFoodDTO);
        partyFood = partyFoodRepository.save(partyFood);
        return partyFoodMapper.toDto(partyFood);
    }

    /**
     * Get all the partyFoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartyFoodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PartyFoods");
        return partyFoodRepository.findAll(pageable)
            .map(partyFoodMapper::toDto);
    }


    /**
     *  get all the partyFoods where AcceptedByHost is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PartyFoodDTO> findAllWhereAcceptedByHostIsNull() {
        log.debug("Request to get all partyFoods where AcceptedByHost is null");
        return StreamSupport
            .stream(partyFoodRepository.findAll().spliterator(), false)
            .filter(partyFood -> partyFood.getAcceptedByHost() == null)
            .map(partyFoodMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one partyFood by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PartyFoodDTO findOne(Long id) {
        log.debug("Request to get PartyFood : {}", id);
        PartyFood partyFood = partyFoodRepository.findOneWithEagerRelationships(id);
        return partyFoodMapper.toDto(partyFood);
    }

    /**
     * Delete the partyFood by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyFood : {}", id);
        partyFoodRepository.delete(id);
    }
}
