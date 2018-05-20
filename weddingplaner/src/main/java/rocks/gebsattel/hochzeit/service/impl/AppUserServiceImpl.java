package rocks.gebsattel.hochzeit.service.impl;

import rocks.gebsattel.hochzeit.service.AppUserService;
import rocks.gebsattel.hochzeit.domain.AppUser;
import rocks.gebsattel.hochzeit.repository.AppUserRepository;
import rocks.gebsattel.hochzeit.repository.search.AppUserSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.AppUserDTO;
import rocks.gebsattel.hochzeit.service.mapper.AppUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AppUser.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    private final AppUserSearchRepository appUserSearchRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository, AppUserMapper appUserMapper, AppUserSearchRepository appUserSearchRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
        this.appUserSearchRepository = appUserSearchRepository;
    }

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        AppUserDTO result = appUserMapper.toDto(appUser);
        appUserSearchRepository.save(appUser);
        return result;
    }

    /**
     * Get all the appUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AppUserDTO> findAll() {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll().stream()
            .map(appUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the appUsers where WeddingGuest is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AppUserDTO> findAllWhereWeddingGuestIsNull() {
        log.debug("Request to get all appUsers where WeddingGuest is null");
        return StreamSupport
            .stream(appUserRepository.findAll().spliterator(), false)
            .filter(appUser -> appUser.getWeddingGuest() == null)
            .map(appUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the appUsers where WeddingHost is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AppUserDTO> findAllWhereWeddingHostIsNull() {
        log.debug("Request to get all appUsers where WeddingHost is null");
        return StreamSupport
            .stream(appUserRepository.findAll().spliterator(), false)
            .filter(appUser -> appUser.getWeddingHost() == null)
            .map(appUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the appUsers where WeddingService is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AppUserDTO> findAllWhereWeddingServiceIsNull() {
        log.debug("Request to get all appUsers where WeddingService is null");
        return StreamSupport
            .stream(appUserRepository.findAll().spliterator(), false)
            .filter(appUser -> appUser.getWeddingService() == null)
            .map(appUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one appUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AppUserDTO findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        AppUser appUser = appUserRepository.findOne(id);
        return appUserMapper.toDto(appUser);
    }

    /**
     * Delete the appUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.delete(id);
        appUserSearchRepository.delete(id);
    }

    /**
     * Search for the appUser corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AppUserDTO> search(String query) {
        log.debug("Request to search AppUsers for query {}", query);
        return StreamSupport
            .stream(appUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(appUserMapper::toDto)
            .collect(Collectors.toList());
    }
}
