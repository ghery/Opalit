package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.HCurrency;

import com.mycompany.myapp.repository.HCurrencyRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing HCurrency.
 */
@RestController
@RequestMapping("/api")
public class HCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(HCurrencyResource.class);

    private static final String ENTITY_NAME = "hCurrency";

    private final HCurrencyRepository hCurrencyRepository;

    public HCurrencyResource(HCurrencyRepository hCurrencyRepository) {
        this.hCurrencyRepository = hCurrencyRepository;
    }

    /**
     * POST  /h-currencies : Create a new hCurrency.
     *
     * @param hCurrency the hCurrency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hCurrency, or with status 400 (Bad Request) if the hCurrency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/h-currencies")
    @Timed
    public ResponseEntity<HCurrency> createHCurrency(@Valid @RequestBody HCurrency hCurrency) throws URISyntaxException {
        log.debug("REST request to save HCurrency : {}", hCurrency);
        if (hCurrency.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hCurrency cannot already have an ID")).body(null);
        }
        HCurrency result = hCurrencyRepository.save(hCurrency);
        return ResponseEntity.created(new URI("/api/h-currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /h-currencies : Updates an existing hCurrency.
     *
     * @param hCurrency the hCurrency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hCurrency,
     * or with status 400 (Bad Request) if the hCurrency is not valid,
     * or with status 500 (Internal Server Error) if the hCurrency couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/h-currencies")
    @Timed
    public ResponseEntity<HCurrency> updateHCurrency(@Valid @RequestBody HCurrency hCurrency) throws URISyntaxException {
        log.debug("REST request to update HCurrency : {}", hCurrency);
        if (hCurrency.getId() == null) {
            return createHCurrency(hCurrency);
        }
        HCurrency result = hCurrencyRepository.save(hCurrency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hCurrency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /h-currencies : get all the hCurrencies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hCurrencies in body
     */
    @GetMapping("/h-currencies")
    @Timed
    public ResponseEntity<List<HCurrency>> getAllHCurrencies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HCurrencies");
        Page<HCurrency> page = hCurrencyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/h-currencies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /h-currencies/:id : get the "id" hCurrency.
     *
     * @param id the id of the hCurrency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hCurrency, or with status 404 (Not Found)
     */
    @GetMapping("/h-currencies/{id}")
    @Timed
    public ResponseEntity<HCurrency> getHCurrency(@PathVariable Long id) {
        log.debug("REST request to get HCurrency : {}", id);
        HCurrency hCurrency = hCurrencyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hCurrency));
    }

    /**
     * DELETE  /h-currencies/:id : delete the "id" hCurrency.
     *
     * @param id the id of the hCurrency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/h-currencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteHCurrency(@PathVariable Long id) {
        log.debug("REST request to delete HCurrency : {}", id);
        hCurrencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
