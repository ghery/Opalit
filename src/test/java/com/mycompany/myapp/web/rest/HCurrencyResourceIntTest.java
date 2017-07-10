package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.HCurrency;
import com.mycompany.myapp.repository.HCurrencyRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HCurrencyResource REST controller.
 *
 * @see HCurrencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class HCurrencyResourceIntTest {

    private static final Double DEFAULT_OP = 1D;
    private static final Double UPDATED_OP = 2D;

    private static final Double DEFAULT_HI = 1D;
    private static final Double UPDATED_HI = 2D;

    private static final Double DEFAULT_LO = 1D;
    private static final Double UPDATED_LO = 2D;

    private static final Double DEFAULT_LA = 1D;
    private static final Double UPDATED_LA = 2D;

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_VOL = 1F;
    private static final Float UPDATED_VOL = 2F;

    private static final Double DEFAULT_PC = 1D;
    private static final Double UPDATED_PC = 2D;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HCurrencyRepository hCurrencyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHCurrencyMockMvc;

    private HCurrency hCurrency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HCurrencyResource hCurrencyResource = new HCurrencyResource(hCurrencyRepository);
        this.restHCurrencyMockMvc = MockMvcBuilders.standaloneSetup(hCurrencyResource)
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
    public static HCurrency createEntity(EntityManager em) {
        HCurrency hCurrency = new HCurrency()
            .op(DEFAULT_OP)
            .hi(DEFAULT_HI)
            .lo(DEFAULT_LO)
            .la(DEFAULT_LA)
            .day(DEFAULT_DAY)
            .vol(DEFAULT_VOL)
            .pc(DEFAULT_PC)
            .time(DEFAULT_TIME);
        return hCurrency;
    }

    @Before
    public void initTest() {
        hCurrency = createEntity(em);
    }

    @Test
    @Transactional
    public void createHCurrency() throws Exception {
        int databaseSizeBeforeCreate = hCurrencyRepository.findAll().size();

        // Create the HCurrency
        restHCurrencyMockMvc.perform(post("/api/h-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hCurrency)))
            .andExpect(status().isCreated());

        // Validate the HCurrency in the database
        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeCreate + 1);
        HCurrency testHCurrency = hCurrencyList.get(hCurrencyList.size() - 1);
        assertThat(testHCurrency.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testHCurrency.getHi()).isEqualTo(DEFAULT_HI);
        assertThat(testHCurrency.getLo()).isEqualTo(DEFAULT_LO);
        assertThat(testHCurrency.getLa()).isEqualTo(DEFAULT_LA);
        assertThat(testHCurrency.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testHCurrency.getVol()).isEqualTo(DEFAULT_VOL);
        assertThat(testHCurrency.getPc()).isEqualTo(DEFAULT_PC);
        assertThat(testHCurrency.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createHCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hCurrencyRepository.findAll().size();

        // Create the HCurrency with an existing ID
        hCurrency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHCurrencyMockMvc.perform(post("/api/h-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hCurrency)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = hCurrencyRepository.findAll().size();
        // set the field null
        hCurrency.setDay(null);

        // Create the HCurrency, which fails.

        restHCurrencyMockMvc.perform(post("/api/h-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hCurrency)))
            .andExpect(status().isBadRequest());

        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hCurrencyRepository.findAll().size();
        // set the field null
        hCurrency.setTime(null);

        // Create the HCurrency, which fails.

        restHCurrencyMockMvc.perform(post("/api/h-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hCurrency)))
            .andExpect(status().isBadRequest());

        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHCurrencies() throws Exception {
        // Initialize the database
        hCurrencyRepository.saveAndFlush(hCurrency);

        // Get all the hCurrencyList
        restHCurrencyMockMvc.perform(get("/api/h-currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hCurrency.getId().intValue())))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP.doubleValue())))
            .andExpect(jsonPath("$.[*].hi").value(hasItem(DEFAULT_HI.doubleValue())))
            .andExpect(jsonPath("$.[*].lo").value(hasItem(DEFAULT_LO.doubleValue())))
            .andExpect(jsonPath("$.[*].la").value(hasItem(DEFAULT_LA.doubleValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].vol").value(hasItem(DEFAULT_VOL.doubleValue())))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(DEFAULT_PC.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getHCurrency() throws Exception {
        // Initialize the database
        hCurrencyRepository.saveAndFlush(hCurrency);

        // Get the hCurrency
        restHCurrencyMockMvc.perform(get("/api/h-currencies/{id}", hCurrency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hCurrency.getId().intValue()))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP.doubleValue()))
            .andExpect(jsonPath("$.hi").value(DEFAULT_HI.doubleValue()))
            .andExpect(jsonPath("$.lo").value(DEFAULT_LO.doubleValue()))
            .andExpect(jsonPath("$.la").value(DEFAULT_LA.doubleValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.vol").value(DEFAULT_VOL.doubleValue()))
            .andExpect(jsonPath("$.pc").value(DEFAULT_PC.doubleValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingHCurrency() throws Exception {
        // Get the hCurrency
        restHCurrencyMockMvc.perform(get("/api/h-currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHCurrency() throws Exception {
        // Initialize the database
        hCurrencyRepository.saveAndFlush(hCurrency);
        int databaseSizeBeforeUpdate = hCurrencyRepository.findAll().size();

        // Update the hCurrency
        HCurrency updatedHCurrency = hCurrencyRepository.findOne(hCurrency.getId());
        updatedHCurrency
            .op(UPDATED_OP)
            .hi(UPDATED_HI)
            .lo(UPDATED_LO)
            .la(UPDATED_LA)
            .day(UPDATED_DAY)
            .vol(UPDATED_VOL)
            .pc(UPDATED_PC)
            .time(UPDATED_TIME);

        restHCurrencyMockMvc.perform(put("/api/h-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHCurrency)))
            .andExpect(status().isOk());

        // Validate the HCurrency in the database
        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeUpdate);
        HCurrency testHCurrency = hCurrencyList.get(hCurrencyList.size() - 1);
        assertThat(testHCurrency.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testHCurrency.getHi()).isEqualTo(UPDATED_HI);
        assertThat(testHCurrency.getLo()).isEqualTo(UPDATED_LO);
        assertThat(testHCurrency.getLa()).isEqualTo(UPDATED_LA);
        assertThat(testHCurrency.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHCurrency.getVol()).isEqualTo(UPDATED_VOL);
        assertThat(testHCurrency.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testHCurrency.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingHCurrency() throws Exception {
        int databaseSizeBeforeUpdate = hCurrencyRepository.findAll().size();

        // Create the HCurrency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHCurrencyMockMvc.perform(put("/api/h-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hCurrency)))
            .andExpect(status().isCreated());

        // Validate the HCurrency in the database
        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHCurrency() throws Exception {
        // Initialize the database
        hCurrencyRepository.saveAndFlush(hCurrency);
        int databaseSizeBeforeDelete = hCurrencyRepository.findAll().size();

        // Get the hCurrency
        restHCurrencyMockMvc.perform(delete("/api/h-currencies/{id}", hCurrency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HCurrency> hCurrencyList = hCurrencyRepository.findAll();
        assertThat(hCurrencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HCurrency.class);
        HCurrency hCurrency1 = new HCurrency();
        hCurrency1.setId(1L);
        HCurrency hCurrency2 = new HCurrency();
        hCurrency2.setId(hCurrency1.getId());
        assertThat(hCurrency1).isEqualTo(hCurrency2);
        hCurrency2.setId(2L);
        assertThat(hCurrency1).isNotEqualTo(hCurrency2);
        hCurrency1.setId(null);
        assertThat(hCurrency1).isNotEqualTo(hCurrency2);
    }
}
