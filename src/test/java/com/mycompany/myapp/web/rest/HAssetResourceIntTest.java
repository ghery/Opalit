package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.HAsset;
import com.mycompany.myapp.repository.HAssetRepository;
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
 * Test class for the HAssetResource REST controller.
 *
 * @see HAssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class HAssetResourceIntTest {

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
    private HAssetRepository hAssetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHAssetMockMvc;

    private HAsset hAsset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HAssetResource hAssetResource = new HAssetResource(hAssetRepository);
        this.restHAssetMockMvc = MockMvcBuilders.standaloneSetup(hAssetResource)
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
    public static HAsset createEntity(EntityManager em) {
        HAsset hAsset = new HAsset()
            .op(DEFAULT_OP)
            .hi(DEFAULT_HI)
            .lo(DEFAULT_LO)
            .la(DEFAULT_LA)
            .day(DEFAULT_DAY)
            .vol(DEFAULT_VOL)
            .pc(DEFAULT_PC)
            .time(DEFAULT_TIME);
        return hAsset;
    }

    @Before
    public void initTest() {
        hAsset = createEntity(em);
    }

    @Test
    @Transactional
    public void createHAsset() throws Exception {
        int databaseSizeBeforeCreate = hAssetRepository.findAll().size();

        // Create the HAsset
        restHAssetMockMvc.perform(post("/api/h-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hAsset)))
            .andExpect(status().isCreated());

        // Validate the HAsset in the database
        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeCreate + 1);
        HAsset testHAsset = hAssetList.get(hAssetList.size() - 1);
        assertThat(testHAsset.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testHAsset.getHi()).isEqualTo(DEFAULT_HI);
        assertThat(testHAsset.getLo()).isEqualTo(DEFAULT_LO);
        assertThat(testHAsset.getLa()).isEqualTo(DEFAULT_LA);
        assertThat(testHAsset.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testHAsset.getVol()).isEqualTo(DEFAULT_VOL);
        assertThat(testHAsset.getPc()).isEqualTo(DEFAULT_PC);
        assertThat(testHAsset.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createHAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hAssetRepository.findAll().size();

        // Create the HAsset with an existing ID
        hAsset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHAssetMockMvc.perform(post("/api/h-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hAsset)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = hAssetRepository.findAll().size();
        // set the field null
        hAsset.setDay(null);

        // Create the HAsset, which fails.

        restHAssetMockMvc.perform(post("/api/h-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hAsset)))
            .andExpect(status().isBadRequest());

        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hAssetRepository.findAll().size();
        // set the field null
        hAsset.setTime(null);

        // Create the HAsset, which fails.

        restHAssetMockMvc.perform(post("/api/h-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hAsset)))
            .andExpect(status().isBadRequest());

        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHAssets() throws Exception {
        // Initialize the database
        hAssetRepository.saveAndFlush(hAsset);

        // Get all the hAssetList
        restHAssetMockMvc.perform(get("/api/h-assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hAsset.getId().intValue())))
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
    public void getHAsset() throws Exception {
        // Initialize the database
        hAssetRepository.saveAndFlush(hAsset);

        // Get the hAsset
        restHAssetMockMvc.perform(get("/api/h-assets/{id}", hAsset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hAsset.getId().intValue()))
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
    public void getNonExistingHAsset() throws Exception {
        // Get the hAsset
        restHAssetMockMvc.perform(get("/api/h-assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHAsset() throws Exception {
        // Initialize the database
        hAssetRepository.saveAndFlush(hAsset);
        int databaseSizeBeforeUpdate = hAssetRepository.findAll().size();

        // Update the hAsset
        HAsset updatedHAsset = hAssetRepository.findOne(hAsset.getId());
        updatedHAsset
            .op(UPDATED_OP)
            .hi(UPDATED_HI)
            .lo(UPDATED_LO)
            .la(UPDATED_LA)
            .day(UPDATED_DAY)
            .vol(UPDATED_VOL)
            .pc(UPDATED_PC)
            .time(UPDATED_TIME);

        restHAssetMockMvc.perform(put("/api/h-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHAsset)))
            .andExpect(status().isOk());

        // Validate the HAsset in the database
        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeUpdate);
        HAsset testHAsset = hAssetList.get(hAssetList.size() - 1);
        assertThat(testHAsset.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testHAsset.getHi()).isEqualTo(UPDATED_HI);
        assertThat(testHAsset.getLo()).isEqualTo(UPDATED_LO);
        assertThat(testHAsset.getLa()).isEqualTo(UPDATED_LA);
        assertThat(testHAsset.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHAsset.getVol()).isEqualTo(UPDATED_VOL);
        assertThat(testHAsset.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testHAsset.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingHAsset() throws Exception {
        int databaseSizeBeforeUpdate = hAssetRepository.findAll().size();

        // Create the HAsset

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHAssetMockMvc.perform(put("/api/h-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hAsset)))
            .andExpect(status().isCreated());

        // Validate the HAsset in the database
        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHAsset() throws Exception {
        // Initialize the database
        hAssetRepository.saveAndFlush(hAsset);
        int databaseSizeBeforeDelete = hAssetRepository.findAll().size();

        // Get the hAsset
        restHAssetMockMvc.perform(delete("/api/h-assets/{id}", hAsset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HAsset> hAssetList = hAssetRepository.findAll();
        assertThat(hAssetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HAsset.class);
        HAsset hAsset1 = new HAsset();
        hAsset1.setId(1L);
        HAsset hAsset2 = new HAsset();
        hAsset2.setId(hAsset1.getId());
        assertThat(hAsset1).isEqualTo(hAsset2);
        hAsset2.setId(2L);
        assertThat(hAsset1).isNotEqualTo(hAsset2);
        hAsset1.setId(null);
        assertThat(hAsset1).isNotEqualTo(hAsset2);
    }
}
