package rocks.gebsattel.hochzeit.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.gebsattel.hochzeit.service.AddressService;
import rocks.gebsattel.hochzeit.web.rest.errors.BadRequestAlertException;
import rocks.gebsattel.hochzeit.web.rest.util.HeaderUtil;
import rocks.gebsattel.hochzeit.web.rest.util.PaginationUtil;
import rocks.gebsattel.hochzeit.service.dto.AddressDTO;
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
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * POST  /addresses : Create a new address.
     *
     * @param addressDTO the addressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressDTO, or with status 400 (Bad Request) if the address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addresses")
    @Timed
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressDTO);
        if (addressDTO.getId() != null) {
            throw new BadRequestAlertException("A new address cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresses : Updates an existing address.
     *
     * @param addressDTO the addressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressDTO,
     * or with status 400 (Bad Request) if the addressDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addresses")
    @Timed
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to update Address : {}", addressDTO);
        if (addressDTO.getId() == null) {
            return createAddress(addressDTO);
        }
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    @Timed
    public ResponseEntity<List<AddressDTO>> getAllAddresses(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("guestprivate-is-null".equals(filter)) {
            log.debug("REST request to get all Addresss where guestPrivate is null");
            return new ResponseEntity<>(addressService.findAllWhereGuestPrivateIsNull(),
                    HttpStatus.OK);
        }
        if ("guestbusiness-is-null".equals(filter)) {
            log.debug("REST request to get all Addresss where guestBusiness is null");
            return new ResponseEntity<>(addressService.findAllWhereGuestBusinessIsNull(),
                    HttpStatus.OK);
        }
        if ("servicebusiness-is-null".equals(filter)) {
            log.debug("REST request to get all Addresss where serviceBusiness is null");
            return new ResponseEntity<>(addressService.findAllWhereServiceBusinessIsNull(),
                    HttpStatus.OK);
        }
        if ("serviceprivate-is-null".equals(filter)) {
            log.debug("REST request to get all Addresss where servicePrivate is null");
            return new ResponseEntity<>(addressService.findAllWhereServicePrivateIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Addresses");
        Page<AddressDTO> page = addressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the addressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        AddressDTO addressDTO = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addressDTO));
    }

    /**
     * DELETE  /addresses/:id : delete the "id" address.
     *
     * @param id the id of the addressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/addresses?query=:query : search for the address corresponding
     * to the query.
     *
     * @param query the query of the address search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/addresses")
    @Timed
    public ResponseEntity<List<AddressDTO>> searchAddresses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Addresses for query {}", query);
        Page<AddressDTO> page = addressService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
