package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.service.WeddingServiceService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import rocks.gebsattel.hochzeit.service.dto.WeddingServiceDTO;
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
 * REST controller for managing WeddingService.
 */
@RestController
@RequestMapping("/api")
public class WeddingServiceResource {

    private final Logger log = LoggerFactory.getLogger(WeddingServiceResource.class);

    private static final String ENTITY_NAME = "weddingService";

    private final WeddingServiceService weddingServiceService;

    public WeddingServiceResource(WeddingServiceService weddingServiceService) {
        this.weddingServiceService = weddingServiceService;
    }

    /**
     * POST  /wedding-services : Create a new weddingService.
     *
     * @param weddingServiceDTO the weddingServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weddingServiceDTO, or with status 400 (Bad Request) if the weddingService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wedding-services")
    @Timed
    public ResponseEntity<WeddingServiceDTO> createWeddingService(@Valid @RequestBody WeddingServiceDTO weddingServiceDTO) throws URISyntaxException {
        log.debug("REST request to save WeddingService : {}", weddingServiceDTO);
        if (weddingServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new weddingService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeddingServiceDTO result = weddingServiceService.save(weddingServiceDTO);
        return ResponseEntity.created(new URI("/api/wedding-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wedding-services : Updates an existing weddingService.
     *
     * @param weddingServiceDTO the weddingServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weddingServiceDTO,
     * or with status 400 (Bad Request) if the weddingServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the weddingServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wedding-services")
    @Timed
    public ResponseEntity<WeddingServiceDTO> updateWeddingService(@Valid @RequestBody WeddingServiceDTO weddingServiceDTO) throws URISyntaxException {
        log.debug("REST request to update WeddingService : {}", weddingServiceDTO);
        if (weddingServiceDTO.getId() == null) {
            return createWeddingService(weddingServiceDTO);
        }
        WeddingServiceDTO result = weddingServiceService.save(weddingServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weddingServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wedding-services : get all the weddingServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weddingServices in body
     */
    @GetMapping("/wedding-services")
    @Timed
    public ResponseEntity<List<WeddingServiceDTO>> getAllWeddingServices(Pageable pageable) {
        log.debug("REST request to get a page of WeddingServices");
        Page<WeddingServiceDTO> page = weddingServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wedding-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wedding-services/:id : get the "id" weddingService.
     *
     * @param id the id of the weddingServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weddingServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wedding-services/{id}")
    @Timed
    public ResponseEntity<WeddingServiceDTO> getWeddingService(@PathVariable Long id) {
        log.debug("REST request to get WeddingService : {}", id);
        WeddingServiceDTO weddingServiceDTO = weddingServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weddingServiceDTO));
    }

    /**
     * DELETE  /wedding-services/:id : delete the "id" weddingService.
     *
     * @param id the id of the weddingServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wedding-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeddingService(@PathVariable Long id) {
        log.debug("REST request to delete WeddingService : {}", id);
        weddingServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wedding-services?query=:query : search for the weddingService corresponding
     * to the query.
     *
     * @param query the query of the weddingService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/wedding-services")
    @Timed
    public ResponseEntity<List<WeddingServiceDTO>> searchWeddingServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WeddingServices for query {}", query);
        Page<WeddingServiceDTO> page = weddingServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/wedding-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
