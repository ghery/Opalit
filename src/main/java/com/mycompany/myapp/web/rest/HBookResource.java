package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.HBook;

import com.mycompany.myapp.repository.HBookRepository;
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
 * REST controller for managing HBook.
 */
@RestController
@RequestMapping("/api")
public class HBookResource {

    private final Logger log = LoggerFactory.getLogger(HBookResource.class);

    private static final String ENTITY_NAME = "hBook";

    private final HBookRepository hBookRepository;

    public HBookResource(HBookRepository hBookRepository) {
        this.hBookRepository = hBookRepository;
    }

    /**
     * POST  /h-books : Create a new hBook.
     *
     * @param hBook the hBook to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hBook, or with status 400 (Bad Request) if the hBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/h-books")
    @Timed
    public ResponseEntity<HBook> createHBook(@Valid @RequestBody HBook hBook) throws URISyntaxException {
        log.debug("REST request to save HBook : {}", hBook);
        if (hBook.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hBook cannot already have an ID")).body(null);
        }
        HBook result = hBookRepository.save(hBook);
        return ResponseEntity.created(new URI("/api/h-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /h-books : Updates an existing hBook.
     *
     * @param hBook the hBook to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hBook,
     * or with status 400 (Bad Request) if the hBook is not valid,
     * or with status 500 (Internal Server Error) if the hBook couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/h-books")
    @Timed
    public ResponseEntity<HBook> updateHBook(@Valid @RequestBody HBook hBook) throws URISyntaxException {
        log.debug("REST request to update HBook : {}", hBook);
        if (hBook.getId() == null) {
            return createHBook(hBook);
        }
        HBook result = hBookRepository.save(hBook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hBook.getId().toString()))
            .body(result);
    }

    /**
     * GET  /h-books : get all the hBooks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hBooks in body
     */
    @GetMapping("/h-books")
    @Timed
    public ResponseEntity<List<HBook>> getAllHBooks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HBooks");
        Page<HBook> page = hBookRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/h-books");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /h-books/:id : get the "id" hBook.
     *
     * @param id the id of the hBook to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hBook, or with status 404 (Not Found)
     */
    @GetMapping("/h-books/{id}")
    @Timed
    public ResponseEntity<HBook> getHBook(@PathVariable Long id) {
        log.debug("REST request to get HBook : {}", id);
        HBook hBook = hBookRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hBook));
    }

    /**
     * DELETE  /h-books/:id : delete the "id" hBook.
     *
     * @param id the id of the hBook to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/h-books/{id}")
    @Timed
    public ResponseEntity<Void> deleteHBook(@PathVariable Long id) {
        log.debug("REST request to delete HBook : {}", id);
        hBookRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
