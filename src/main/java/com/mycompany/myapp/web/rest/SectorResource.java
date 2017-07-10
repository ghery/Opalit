package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Sector;

import com.mycompany.myapp.repository.SectorRepository;
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
 * REST controller for managing Sector.
 */
@RestController
@RequestMapping("/api")
public class SectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorResource.class);

    private static final String ENTITY_NAME = "sector";

    private final SectorRepository sectorRepository;

    public SectorResource(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    /**
     * POST  /sectors : Create a new sector.
     *
     * @param sector the sector to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sector, or with status 400 (Bad Request) if the sector has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sectors")
    @Timed
    public ResponseEntity<Sector> createSector(@RequestBody Sector sector) throws URISyntaxException {
        log.debug("REST request to save Sector : {}", sector);
        if (sector.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sector cannot already have an ID")).body(null);
        }
        Sector result = sectorRepository.save(sector);
        return ResponseEntity.created(new URI("/api/sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sectors : Updates an existing sector.
     *
     * @param sector the sector to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sector,
     * or with status 400 (Bad Request) if the sector is not valid,
     * or with status 500 (Internal Server Error) if the sector couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sectors")
    @Timed
    public ResponseEntity<Sector> updateSector(@RequestBody Sector sector) throws URISyntaxException {
        log.debug("REST request to update Sector : {}", sector);
        if (sector.getId() == null) {
            return createSector(sector);
        }
        Sector result = sectorRepository.save(sector);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sector.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sectors : get all the sectors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sectors in body
     */
    @GetMapping("/sectors")
    @Timed
    public ResponseEntity<List<Sector>> getAllSectors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Sectors");
        Page<Sector> page = sectorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sectors/:id : get the "id" sector.
     *
     * @param id the id of the sector to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sector, or with status 404 (Not Found)
     */
    @GetMapping("/sectors/{id}")
    @Timed
    public ResponseEntity<Sector> getSector(@PathVariable Long id) {
        log.debug("REST request to get Sector : {}", id);
        Sector sector = sectorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sector));
    }

    /**
     * DELETE  /sectors/:id : delete the "id" sector.
     *
     * @param id the id of the sector to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sectors/{id}")
    @Timed
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        log.debug("REST request to delete Sector : {}", id);
        sectorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
