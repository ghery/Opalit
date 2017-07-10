package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.Calcul;
import com.mycompany.myapp.repository.CalculRepository;
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
 * Test class for the CalculResource REST controller.
 *
 * @see CalculResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class CalculResourceIntTest {

    private static final Integer DEFAULT_MARKETVALUE = 1;
    private static final Integer UPDATED_MARKETVALUE = 2;

    @Autowired
    private CalculRepository calculRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCalculMockMvc;

    private Calcul calcul;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalculResource calculResource = new CalculResource(calculRepository);
        this.restCalculMockMvc = MockMvcBuilders.standaloneSetup(calculResource)
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
    public static Calcul createEntity(EntityManager em) {
        Calcul calcul = new Calcul()
            .marketvalue(DEFAULT_MARKETVALUE);
        return calcul;
    }

    @Before
    public void initTest() {
        calcul = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalcul() throws Exception {
        int databaseSizeBeforeCreate = calculRepository.findAll().size();

        // Create the Calcul
        restCalculMockMvc.perform(post("/api/calculs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calcul)))
            .andExpect(status().isCreated());

        // Validate the Calcul in the database
        List<Calcul> calculList = calculRepository.findAll();
        assertThat(calculList).hasSize(databaseSizeBeforeCreate + 1);
        Calcul testCalcul = calculList.get(calculList.size() - 1);
        assertThat(testCalcul.getMarketvalue()).isEqualTo(DEFAULT_MARKETVALUE);
    }

    @Test
    @Transactional
    public void createCalculWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calculRepository.findAll().size();

        // Create the Calcul with an existing ID
        calcul.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalculMockMvc.perform(post("/api/calculs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calcul)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Calcul> calculList = calculRepository.findAll();
        assertThat(calculList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCalculs() throws Exception {
        // Initialize the database
        calculRepository.saveAndFlush(calcul);

        // Get all the calculList
        restCalculMockMvc.perform(get("/api/calculs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calcul.getId().intValue())))
            .andExpect(jsonPath("$.[*].marketvalue").value(hasItem(DEFAULT_MARKETVALUE)));
    }

    @Test
    @Transactional
    public void getCalcul() throws Exception {
        // Initialize the database
        calculRepository.saveAndFlush(calcul);

        // Get the calcul
        restCalculMockMvc.perform(get("/api/calculs/{id}", calcul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calcul.getId().intValue()))
            .andExpect(jsonPath("$.marketvalue").value(DEFAULT_MARKETVALUE));
    }

    @Test
    @Transactional
    public void getNonExistingCalcul() throws Exception {
        // Get the calcul
        restCalculMockMvc.perform(get("/api/calculs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalcul() throws Exception {
        // Initialize the database
        calculRepository.saveAndFlush(calcul);
        int databaseSizeBeforeUpdate = calculRepository.findAll().size();

        // Update the calcul
        Calcul updatedCalcul = calculRepository.findOne(calcul.getId());
        updatedCalcul
            .marketvalue(UPDATED_MARKETVALUE);

        restCalculMockMvc.perform(put("/api/calculs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalcul)))
            .andExpect(status().isOk());

        // Validate the Calcul in the database
        List<Calcul> calculList = calculRepository.findAll();
        assertThat(calculList).hasSize(databaseSizeBeforeUpdate);
        Calcul testCalcul = calculList.get(calculList.size() - 1);
        assertThat(testCalcul.getMarketvalue()).isEqualTo(UPDATED_MARKETVALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCalcul() throws Exception {
        int databaseSizeBeforeUpdate = calculRepository.findAll().size();

        // Create the Calcul

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalculMockMvc.perform(put("/api/calculs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calcul)))
            .andExpect(status().isCreated());

        // Validate the Calcul in the database
        List<Calcul> calculList = calculRepository.findAll();
        assertThat(calculList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCalcul() throws Exception {
        // Initialize the database
        calculRepository.saveAndFlush(calcul);
        int databaseSizeBeforeDelete = calculRepository.findAll().size();

        // Get the calcul
        restCalculMockMvc.perform(delete("/api/calculs/{id}", calcul.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calcul> calculList = calculRepository.findAll();
        assertThat(calculList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calcul.class);
        Calcul calcul1 = new Calcul();
        calcul1.setId(1L);
        Calcul calcul2 = new Calcul();
        calcul2.setId(calcul1.getId());
        assertThat(calcul1).isEqualTo(calcul2);
        calcul2.setId(2L);
        assertThat(calcul1).isNotEqualTo(calcul2);
        calcul1.setId(null);
        assertThat(calcul1).isNotEqualTo(calcul2);
    }
}
