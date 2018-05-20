package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.service.PartyEventService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import rocks.gebsattel.hochzeit.service.dto.PartyEventDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PartyEvent.
 */
@RestController
@RequestMapping("/api")
public class PartyEventResource {

    private final Logger log = LoggerFactory.getLogger(PartyEventResource.class);

    private static final String ENTITY_NAME = "partyEvent";

    private final PartyEventService partyEventService;

    public PartyEventResource(PartyEventService partyEventService) {
        this.partyEventService = partyEventService;
    }

    /**
     * POST  /party-events : Create a new partyEvent.
     *
     * @param partyEventDTO the partyEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partyEventDTO, or with status 400 (Bad Request) if the partyEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/party-events")
    @Timed
    public ResponseEntity<PartyEventDTO> createPartyEvent(@Valid @RequestBody PartyEventDTO partyEventDTO) throws URISyntaxException {
        log.debug("REST request to save PartyEvent : {}", partyEventDTO);
        if (partyEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyEventDTO result = partyEventService.save(partyEventDTO);
        return ResponseEntity.created(new URI("/api/party-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /party-events : Updates an existing partyEvent.
     *
     * @param partyEventDTO the partyEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partyEventDTO,
     * or with status 400 (Bad Request) if the partyEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the partyEventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/party-events")
    @Timed
    public ResponseEntity<PartyEventDTO> updatePartyEvent(@Valid @RequestBody PartyEventDTO partyEventDTO) throws URISyntaxException {
        log.debug("REST request to update PartyEvent : {}", partyEventDTO);
        if (partyEventDTO.getId() == null) {
            return createPartyEvent(partyEventDTO);
        }
        PartyEventDTO result = partyEventService.save(partyEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partyEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /party-events : get all the partyEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of partyEvents in body
     */
    @GetMapping("/party-events")
    @Timed
    public ResponseEntity<List<PartyEventDTO>> getAllPartyEvents(Pageable pageable) {
        log.debug("REST request to get a page of PartyEvents");
        Page<PartyEventDTO> page = partyEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/party-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /party-events/:id : get the "id" partyEvent.
     *
     * @param id the id of the partyEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partyEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/party-events/{id}")
    @Timed
    public ResponseEntity<PartyEventDTO> getPartyEvent(@PathVariable Long id) {
        log.debug("REST request to get PartyEvent : {}", id);
        PartyEventDTO partyEventDTO = partyEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partyEventDTO));
    }

    /**
     * DELETE  /party-events/:id : delete the "id" partyEvent.
     *
     * @param id the id of the partyEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/party-events/{id}")
    @Timed
    public ResponseEntity<Void> deletePartyEvent(@PathVariable Long id) {
        log.debug("REST request to delete PartyEvent : {}", id);
        partyEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/party-events?query=:query : search for the partyEvent corresponding
     * to the query.
     *
     * @param query the query of the partyEvent search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/party-events")
    @Timed
    public ResponseEntity<List<PartyEventDTO>> searchPartyEvents(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PartyEvents for query {}", query);
        Page<PartyEventDTO> page = partyEventService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/party-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
