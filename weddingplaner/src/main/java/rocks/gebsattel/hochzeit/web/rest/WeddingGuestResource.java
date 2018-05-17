package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.security.SecurityUtils;
import rocks.gebsattel.hochzeit.service.WeddingGuestService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import rocks.gebsattel.hochzeit.service.dto.WeddingGuestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import static rocks.gebsattel.hochzeit.security.AuthoritiesConstants.ADMIN;

/**
 * REST controller for managing WeddingGuest.
 */
@RestController
@RequestMapping("/api")
public class WeddingGuestResource {

    private final Logger log = LoggerFactory.getLogger(WeddingGuestResource.class);

    private static final String ENTITY_NAME = "weddingGuest";

    private final WeddingGuestService weddingGuestService;

    public WeddingGuestResource(WeddingGuestService weddingGuestService) {
        this.weddingGuestService = weddingGuestService;
    }

    /**
     * POST  /wedding-guests : Create a new weddingGuest.
     *
     * @param weddingGuestDTO the weddingGuestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weddingGuestDTO, or with status 400 (Bad Request) if the weddingGuest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wedding-guests")
    @Timed
    public ResponseEntity<WeddingGuestDTO> createWeddingGuest(@Valid @RequestBody WeddingGuestDTO weddingGuestDTO) throws URISyntaxException {

        if (!SecurityUtils.isCurrentUserInRole(ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"not-authenticated","You need to be logged in as Admin to perform this action.")).body(null);
            // throw new BadRequestAlertException("You need to be logged in as Admin to perform this action.", ENTITY_NAME, "not-authenticated");
        }

        log.debug("REST request to save WeddingGuest : {}", weddingGuestDTO);
        if (weddingGuestDTO.getId() != null) {
            throw new BadRequestAlertException("A new weddingGuest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeddingGuestDTO result = weddingGuestService.save(weddingGuestDTO);
        return ResponseEntity.created(new URI("/api/wedding-guests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wedding-guests : Updates an existing weddingGuest.
     *
     * @param weddingGuestDTO the weddingGuestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weddingGuestDTO,
     * or with status 400 (Bad Request) if the weddingGuestDTO is not valid,
     * or with status 500 (Internal Server Error) if the weddingGuestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wedding-guests")
    @Timed
    public ResponseEntity<WeddingGuestDTO> updateWeddingGuest(@Valid @RequestBody WeddingGuestDTO weddingGuestDTO) throws URISyntaxException {
        log.debug("REST request to update WeddingGuest : {}", weddingGuestDTO);
        if (weddingGuestDTO.getId() == null) {
            return createWeddingGuest(weddingGuestDTO);
        }
        WeddingGuestDTO result = weddingGuestService.save(weddingGuestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weddingGuestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wedding-guests : get all the weddingGuests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weddingGuests in body
     */
    @GetMapping("/wedding-guests")
    @Timed
    public ResponseEntity<List<WeddingGuestDTO>> getAllWeddingGuests(Pageable pageable) {
        log.debug("REST request to get a page of WeddingGuests");
        Page<WeddingGuestDTO> page = weddingGuestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wedding-guests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wedding-guests/:id : get the "id" weddingGuest.
     *
     * @param id the id of the weddingGuestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weddingGuestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wedding-guests/{id}")
    @Timed
    public ResponseEntity<WeddingGuestDTO> getWeddingGuest(@PathVariable Long id) {
        log.debug("REST request to get WeddingGuest : {}", id);
        WeddingGuestDTO weddingGuestDTO = weddingGuestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weddingGuestDTO));
    }

    /**
     * DELETE  /wedding-guests/:id : delete the "id" weddingGuest.
     *
     * @param id the id of the weddingGuestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wedding-guests/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeddingGuest(@PathVariable Long id) {
        log.debug("REST request to delete WeddingGuest : {}", id);
        weddingGuestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
