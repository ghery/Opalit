package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.Fund;
import com.mycompany.myapp.repository.FundRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FundResource REST controller.
 *
 * @see FundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class FundResourceIntTest {

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFundMockMvc;

    private Fund fund;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FundResource fundResource = new FundResource(fundRepository);
        this.restFundMockMvc = MockMvcBuilders.standaloneSetup(fundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fund createEntity(EntityManager em) {
        Fund fund = new Fund();
        return fund;
    }

    @Before
    public void initTest() {
        fund = createEntity(em);
    }

    @Test
    @Transactional
    public void createFund() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();

        // Create the Fund
        restFundMockMvc.perform(post("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isCreated());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeCreate + 1);
        Fund testFund = fundList.get(fundList.size() - 1);
    }

    @Test
    @Transactional
    public void createFundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();

        // Create the Fund with an existing ID
        fund.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundMockMvc.perform(post("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFunds() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get all the fundList
        restFundMockMvc.perform(get("/api/funds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fund.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", fund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fund.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFund() throws Exception {
        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);
        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // Update the fund
        Fund updatedFund = fundRepository.findOne(fund.getId());

        restFundMockMvc.perform(put("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFund)))
            .andExpect(status().isOk());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeUpdate);
        Fund testFund = fundList.get(fundList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFund() throws Exception {
        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // Create the Fund

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFundMockMvc.perform(put("/api/funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isCreated());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);
        int databaseSizeBeforeDelete = fundRepository.findAll().size();

        // Get the fund
        restFundMockMvc.perform(delete("/api/funds/{id}", fund.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fund.class);
        Fund fund1 = new Fund();
        fund1.setId(1L);
        Fund fund2 = new Fund();
        fund2.setId(fund1.getId());
        assertThat(fund1).isEqualTo(fund2);
        fund2.setId(2L);
        assertThat(fund1).isNotEqualTo(fund2);
        fund1.setId(null);
        assertThat(fund1).isNotEqualTo(fund2);
    }
}
