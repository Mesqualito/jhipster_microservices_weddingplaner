package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.service.WeddingHostService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import rocks.gebsattel.hochzeit.service.dto.WeddingHostDTO;
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

/**
 * REST controller for managing WeddingHost.
 */
@RestController
@RequestMapping("/api")
public class WeddingHostResource {

    private final Logger log = LoggerFactory.getLogger(WeddingHostResource.class);

    private static final String ENTITY_NAME = "weddingHost";

    private final WeddingHostService weddingHostService;

    public WeddingHostResource(WeddingHostService weddingHostService) {
        this.weddingHostService = weddingHostService;
    }

    /**
     * POST  /wedding-hosts : Create a new weddingHost.
     *
     * @param weddingHostDTO the weddingHostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weddingHostDTO, or with status 400 (Bad Request) if the weddingHost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wedding-hosts")
    @Timed
    public ResponseEntity<WeddingHostDTO> createWeddingHost(@Valid @RequestBody WeddingHostDTO weddingHostDTO) throws URISyntaxException {
        log.debug("REST request to save WeddingHost : {}", weddingHostDTO);
        if (weddingHostDTO.getId() != null) {
            throw new BadRequestAlertException("A new weddingHost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeddingHostDTO result = weddingHostService.save(weddingHostDTO);
        return ResponseEntity.created(new URI("/api/wedding-hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wedding-hosts : Updates an existing weddingHost.
     *
     * @param weddingHostDTO the weddingHostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weddingHostDTO,
     * or with status 400 (Bad Request) if the weddingHostDTO is not valid,
     * or with status 500 (Internal Server Error) if the weddingHostDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wedding-hosts")
    @Timed
    public ResponseEntity<WeddingHostDTO> updateWeddingHost(@Valid @RequestBody WeddingHostDTO weddingHostDTO) throws URISyntaxException {
        log.debug("REST request to update WeddingHost : {}", weddingHostDTO);
        if (weddingHostDTO.getId() == null) {
            return createWeddingHost(weddingHostDTO);
        }
        WeddingHostDTO result = weddingHostService.save(weddingHostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weddingHostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wedding-hosts : get all the weddingHosts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weddingHosts in body
     */
    @GetMapping("/wedding-hosts")
    @Timed
    public ResponseEntity<List<WeddingHostDTO>> getAllWeddingHosts(Pageable pageable) {
        log.debug("REST request to get a page of WeddingHosts");
        Page<WeddingHostDTO> page = weddingHostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wedding-hosts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wedding-hosts/:id : get the "id" weddingHost.
     *
     * @param id the id of the weddingHostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weddingHostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wedding-hosts/{id}")
    @Timed
    public ResponseEntity<WeddingHostDTO> getWeddingHost(@PathVariable Long id) {
        log.debug("REST request to get WeddingHost : {}", id);
        WeddingHostDTO weddingHostDTO = weddingHostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weddingHostDTO));
    }

    /**
     * DELETE  /wedding-hosts/:id : delete the "id" weddingHost.
     *
     * @param id the id of the weddingHostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wedding-hosts/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeddingHost(@PathVariable Long id) {
        log.debug("REST request to delete WeddingHost : {}", id);
        weddingHostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
