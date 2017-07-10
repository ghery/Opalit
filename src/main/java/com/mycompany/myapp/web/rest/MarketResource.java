package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Market;

import com.mycompany.myapp.repository.MarketRepository;
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
 * REST controller for managing Market.
 */
@RestController
@RequestMapping("/api")
public class MarketResource {

    private final Logger log = LoggerFactory.getLogger(MarketResource.class);

    private static final String ENTITY_NAME = "market";

    private final MarketRepository marketRepository;

    public MarketResource(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    /**
     * POST  /markets : Create a new market.
     *
     * @param market the market to create
     * @return the ResponseEntity with status 201 (Created) and with body the new market, or with status 400 (Bad Request) if the market has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/markets")
    @Timed
    public ResponseEntity<Market> createMarket(@RequestBody Market market) throws URISyntaxException {
        log.debug("REST request to save Market : {}", market);
        if (market.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new market cannot already have an ID")).body(null);
        }
        Market result = marketRepository.save(market);
        return ResponseEntity.created(new URI("/api/markets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /markets : Updates an existing market.
     *
     * @param market the market to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated market,
     * or with status 400 (Bad Request) if the market is not valid,
     * or with status 500 (Internal Server Error) if the market couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/markets")
    @Timed
    public ResponseEntity<Market> updateMarket(@RequestBody Market market) throws URISyntaxException {
        log.debug("REST request to update Market : {}", market);
        if (market.getId() == null) {
            return createMarket(market);
        }
        Market result = marketRepository.save(market);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, market.getId().toString()))
            .body(result);
    }

    /**
     * GET  /markets : get all the markets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of markets in body
     */
    @GetMapping("/markets")
    @Timed
    public ResponseEntity<List<Market>> getAllMarkets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Markets");
        Page<Market> page = marketRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/markets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /markets/:id : get the "id" market.
     *
     * @param id the id of the market to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the market, or with status 404 (Not Found)
     */
    @GetMapping("/markets/{id}")
    @Timed
    public ResponseEntity<Market> getMarket(@PathVariable Long id) {
        log.debug("REST request to get Market : {}", id);
        Market market = marketRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(market));
    }

    /**
     * DELETE  /markets/:id : delete the "id" market.
     *
     * @param id the id of the market to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/markets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarket(@PathVariable Long id) {
        log.debug("REST request to delete Market : {}", id);
        marketRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
