package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Calcul;

import com.mycompany.myapp.repository.CalculRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Calcul.
 */
@RestController
@RequestMapping("/api")
public class CalculResource {

    private final Logger log = LoggerFactory.getLogger(CalculResource.class);

    private static final String ENTITY_NAME = "calcul";

    private final CalculRepository calculRepository;

    public CalculResource(CalculRepository calculRepository) {
        this.calculRepository = calculRepository;
    }

    /**
     * POST  /calculs : Create a new calcul.
     *
     * @param calcul the calcul to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calcul, or with status 400 (Bad Request) if the calcul has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calculs")
    @Timed
    public ResponseEntity<Calcul> createCalcul(@RequestBody Calcul calcul) throws URISyntaxException {
        log.debug("REST request to save Calcul : {}", calcul);
        if (calcul.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new calcul cannot already have an ID")).body(null);
        }
        Calcul result = calculRepository.save(calcul);
        return ResponseEntity.created(new URI("/api/calculs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calculs : Updates an existing calcul.
     *
     * @param calcul the calcul to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calcul,
     * or with status 400 (Bad Request) if the calcul is not valid,
     * or with status 500 (Internal Server Error) if the calcul couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calculs")
    @Timed
    public ResponseEntity<Calcul> updateCalcul(@RequestBody Calcul calcul) throws URISyntaxException {
        log.debug("REST request to update Calcul : {}", calcul);
        if (calcul.getId() == null) {
            return createCalcul(calcul);
        }
        Calcul result = calculRepository.save(calcul);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calcul.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calculs : get all the calculs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of calculs in body
     */
    @GetMapping("/calculs")
    @Timed
    public ResponseEntity<List<Calcul>> getAllCalculs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Calculs");
        Page<Calcul> page = calculRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/calculs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /calculs/:id : get the "id" calcul.
     *
     * @param id the id of the calcul to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calcul, or with status 404 (Not Found)
     */
    @GetMapping("/calculs/{id}")
    @Timed
    public ResponseEntity<Calcul> getCalcul(@PathVariable Long id) {
        log.debug("REST request to get Calcul : {}", id);
        Calcul calcul = calculRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(calcul));
    }

    /**
     * DELETE  /calculs/:id : delete the "id" calcul.
     *
     * @param id the id of the calcul to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calculs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalcul(@PathVariable Long id) {
        log.debug("REST request to delete Calcul : {}", id);
        calculRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
