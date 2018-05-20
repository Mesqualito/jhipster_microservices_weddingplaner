package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.service.PartyFoodService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import rocks.gebsattel.hochzeit.service.dto.PartyFoodDTO;
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
 * REST controller for managing PartyFood.
 */
@RestController
@RequestMapping("/api")
public class PartyFoodResource {

    private final Logger log = LoggerFactory.getLogger(PartyFoodResource.class);

    private static final String ENTITY_NAME = "partyFood";

    private final PartyFoodService partyFoodService;

    public PartyFoodResource(PartyFoodService partyFoodService) {
        this.partyFoodService = partyFoodService;
    }

    /**
     * POST  /party-foods : Create a new partyFood.
     *
     * @param partyFoodDTO the partyFoodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partyFoodDTO, or with status 400 (Bad Request) if the partyFood has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/party-foods")
    @Timed
    public ResponseEntity<PartyFoodDTO> createPartyFood(@Valid @RequestBody PartyFoodDTO partyFoodDTO) throws URISyntaxException {
        log.debug("REST request to save PartyFood : {}", partyFoodDTO);
        if (partyFoodDTO.getId() != null) {
            throw new BadRequestAlertException("A new partyFood cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyFoodDTO result = partyFoodService.save(partyFoodDTO);
        return ResponseEntity.created(new URI("/api/party-foods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /party-foods : Updates an existing partyFood.
     *
     * @param partyFoodDTO the partyFoodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partyFoodDTO,
     * or with status 400 (Bad Request) if the partyFoodDTO is not valid,
     * or with status 500 (Internal Server Error) if the partyFoodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/party-foods")
    @Timed
    public ResponseEntity<PartyFoodDTO> updatePartyFood(@Valid @RequestBody PartyFoodDTO partyFoodDTO) throws URISyntaxException {
        log.debug("REST request to update PartyFood : {}", partyFoodDTO);
        if (partyFoodDTO.getId() == null) {
            return createPartyFood(partyFoodDTO);
        }
        PartyFoodDTO result = partyFoodService.save(partyFoodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partyFoodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /party-foods : get all the partyFoods.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of partyFoods in body
     */
    @GetMapping("/party-foods")
    @Timed
    public ResponseEntity<List<PartyFoodDTO>> getAllPartyFoods(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("acceptedbyhost-is-null".equals(filter)) {
            log.debug("REST request to get all PartyFoods where acceptedByHost is null");
            return new ResponseEntity<>(partyFoodService.findAllWhereAcceptedByHostIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of PartyFoods");
        Page<PartyFoodDTO> page = partyFoodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/party-foods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /party-foods/:id : get the "id" partyFood.
     *
     * @param id the id of the partyFoodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partyFoodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/party-foods/{id}")
    @Timed
    public ResponseEntity<PartyFoodDTO> getPartyFood(@PathVariable Long id) {
        log.debug("REST request to get PartyFood : {}", id);
        PartyFoodDTO partyFoodDTO = partyFoodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partyFoodDTO));
    }

    /**
     * DELETE  /party-foods/:id : delete the "id" partyFood.
     *
     * @param id the id of the partyFoodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/party-foods/{id}")
    @Timed
    public ResponseEntity<Void> deletePartyFood(@PathVariable Long id) {
        log.debug("REST request to delete PartyFood : {}", id);
        partyFoodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/party-foods?query=:query : search for the partyFood corresponding
     * to the query.
     *
     * @param query the query of the partyFood search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/party-foods")
    @Timed
    public ResponseEntity<List<PartyFoodDTO>> searchPartyFoods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PartyFoods for query {}", query);
        Page<PartyFoodDTO> page = partyFoodService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/party-foods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
