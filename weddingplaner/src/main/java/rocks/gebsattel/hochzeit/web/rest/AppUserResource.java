package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.service.AppUserService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.service.dto.AppUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AppUser.
 */
@RestController
@RequestMapping("/api")
public class AppUserResource {

    private final Logger log = LoggerFactory.getLogger(AppUserResource.class);

    private static final String ENTITY_NAME = "appUser";

    private final AppUserService appUserService;

    public AppUserResource(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * POST  /app-users : Create a new appUser.
     *
     * @param appUserDTO the appUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appUserDTO, or with status 400 (Bad Request) if the appUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/app-users")
    @Timed
    public ResponseEntity<AppUserDTO> createAppUser(@Valid @RequestBody AppUserDTO appUserDTO) throws URISyntaxException {
        log.debug("REST request to save AppUser : {}", appUserDTO);
        if (appUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new appUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppUserDTO result = appUserService.save(appUserDTO);
        return ResponseEntity.created(new URI("/api/app-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /app-users : Updates an existing appUser.
     *
     * @param appUserDTO the appUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appUserDTO,
     * or with status 400 (Bad Request) if the appUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the appUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/app-users")
    @Timed
    public ResponseEntity<AppUserDTO> updateAppUser(@Valid @RequestBody AppUserDTO appUserDTO) throws URISyntaxException {
        log.debug("REST request to update AppUser : {}", appUserDTO);
        if (appUserDTO.getId() == null) {
            return createAppUser(appUserDTO);
        }
        AppUserDTO result = appUserService.save(appUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /app-users : get all the appUsers.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of appUsers in body
     */
    @GetMapping("/app-users")
    @Timed
    public List<AppUserDTO> getAllAppUsers(@RequestParam(required = false) String filter) {
        if ("weddingguest-is-null".equals(filter)) {
            log.debug("REST request to get all AppUsers where weddingGuest is null");
            return appUserService.findAllWhereWeddingGuestIsNull();
        }
        if ("weddinghost-is-null".equals(filter)) {
            log.debug("REST request to get all AppUsers where weddingHost is null");
            return appUserService.findAllWhereWeddingHostIsNull();
        }
        if ("weddingservice-is-null".equals(filter)) {
            log.debug("REST request to get all AppUsers where weddingService is null");
            return appUserService.findAllWhereWeddingServiceIsNull();
        }
        log.debug("REST request to get all AppUsers");
        return appUserService.findAll();
        }

    /**
     * GET  /app-users/:id : get the "id" appUser.
     *
     * @param id the id of the appUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/app-users/{id}")
    @Timed
    public ResponseEntity<AppUserDTO> getAppUser(@PathVariable Long id) {
        log.debug("REST request to get AppUser : {}", id);
        AppUserDTO appUserDTO = appUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appUserDTO));
    }

    /**
     * DELETE  /app-users/:id : delete the "id" appUser.
     *
     * @param id the id of the appUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/app-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long id) {
        log.debug("REST request to delete AppUser : {}", id);
        appUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/app-users?query=:query : search for the appUser corresponding
     * to the query.
     *
     * @param query the query of the appUser search
     * @return the result of the search
     */
    @GetMapping("/_search/app-users")
    @Timed
    public List<AppUserDTO> searchAppUsers(@RequestParam String query) {
        log.debug("REST request to search AppUsers for query {}", query);
        return appUserService.search(query);
    }

}
