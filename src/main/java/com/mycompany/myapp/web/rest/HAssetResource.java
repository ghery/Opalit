package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.HAsset;

import com.mycompany.myapp.repository.HAssetRepository;
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
 * REST controller for managing HAsset.
 */
@RestController
@RequestMapping("/api")
public class HAssetResource {

    private final Logger log = LoggerFactory.getLogger(HAssetResource.class);

    private static final String ENTITY_NAME = "hAsset";

    private final HAssetRepository hAssetRepository;

    public HAssetResource(HAssetRepository hAssetRepository) {
        this.hAssetRepository = hAssetRepository;
    }

    /**
     * POST  /h-assets : Create a new hAsset.
     *
     * @param hAsset the hAsset to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hAsset, or with status 400 (Bad Request) if the hAsset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/h-assets")
    @Timed
    public ResponseEntity<HAsset> createHAsset(@Valid @RequestBody HAsset hAsset) throws URISyntaxException {
        log.debug("REST request to save HAsset : {}", hAsset);
        if (hAsset.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hAsset cannot already have an ID")).body(null);
        }
        HAsset result = hAssetRepository.save(hAsset);
        return ResponseEntity.created(new URI("/api/h-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /h-assets : Updates an existing hAsset.
     *
     * @param hAsset the hAsset to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hAsset,
     * or with status 400 (Bad Request) if the hAsset is not valid,
     * or with status 500 (Internal Server Error) if the hAsset couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/h-assets")
    @Timed
    public ResponseEntity<HAsset> updateHAsset(@Valid @RequestBody HAsset hAsset) throws URISyntaxException {
        log.debug("REST request to update HAsset : {}", hAsset);
        if (hAsset.getId() == null) {
            return createHAsset(hAsset);
        }
        HAsset result = hAssetRepository.save(hAsset);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hAsset.getId().toString()))
            .body(result);
    }

    /**
     * GET  /h-assets : get all the hAssets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hAssets in body
     */
    @GetMapping("/h-assets")
    @Timed
    public ResponseEntity<List<HAsset>> getAllHAssets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HAssets");
        Page<HAsset> page = hAssetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/h-assets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /h-assets/:id : get the "id" hAsset.
     *
     * @param id the id of the hAsset to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hAsset, or with status 404 (Not Found)
     */
    @GetMapping("/h-assets/{id}")
    @Timed
    public ResponseEntity<HAsset> getHAsset(@PathVariable Long id) {
        log.debug("REST request to get HAsset : {}", id);
        HAsset hAsset = hAssetRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hAsset));
    }

    /**
     * DELETE  /h-assets/:id : delete the "id" hAsset.
     *
     * @param id the id of the hAsset to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/h-assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteHAsset(@PathVariable Long id) {
        log.debug("REST request to delete HAsset : {}", id);
        hAssetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
