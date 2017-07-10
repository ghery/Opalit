package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.HBook;
import com.mycompany.myapp.repository.HBookRepository;
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
 * Test class for the HBookResource REST controller.
 *
 * @see HBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class HBookResourceIntTest {

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
    private HBookRepository hBookRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHBookMockMvc;

    private HBook hBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HBookResource hBookResource = new HBookResource(hBookRepository);
        this.restHBookMockMvc = MockMvcBuilders.standaloneSetup(hBookResource)
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
    public static HBook createEntity(EntityManager em) {
        HBook hBook = new HBook()
            .op(DEFAULT_OP)
            .hi(DEFAULT_HI)
            .lo(DEFAULT_LO)
            .la(DEFAULT_LA)
            .day(DEFAULT_DAY)
            .vol(DEFAULT_VOL)
            .pc(DEFAULT_PC)
            .time(DEFAULT_TIME);
        return hBook;
    }

    @Before
    public void initTest() {
        hBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createHBook() throws Exception {
        int databaseSizeBeforeCreate = hBookRepository.findAll().size();

        // Create the HBook
        restHBookMockMvc.perform(post("/api/h-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hBook)))
            .andExpect(status().isCreated());

        // Validate the HBook in the database
        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeCreate + 1);
        HBook testHBook = hBookList.get(hBookList.size() - 1);
        assertThat(testHBook.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testHBook.getHi()).isEqualTo(DEFAULT_HI);
        assertThat(testHBook.getLo()).isEqualTo(DEFAULT_LO);
        assertThat(testHBook.getLa()).isEqualTo(DEFAULT_LA);
        assertThat(testHBook.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testHBook.getVol()).isEqualTo(DEFAULT_VOL);
        assertThat(testHBook.getPc()).isEqualTo(DEFAULT_PC);
        assertThat(testHBook.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createHBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hBookRepository.findAll().size();

        // Create the HBook with an existing ID
        hBook.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHBookMockMvc.perform(post("/api/h-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hBook)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = hBookRepository.findAll().size();
        // set the field null
        hBook.setDay(null);

        // Create the HBook, which fails.

        restHBookMockMvc.perform(post("/api/h-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hBook)))
            .andExpect(status().isBadRequest());

        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hBookRepository.findAll().size();
        // set the field null
        hBook.setTime(null);

        // Create the HBook, which fails.

        restHBookMockMvc.perform(post("/api/h-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hBook)))
            .andExpect(status().isBadRequest());

        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHBooks() throws Exception {
        // Initialize the database
        hBookRepository.saveAndFlush(hBook);

        // Get all the hBookList
        restHBookMockMvc.perform(get("/api/h-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hBook.getId().intValue())))
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
    public void getHBook() throws Exception {
        // Initialize the database
        hBookRepository.saveAndFlush(hBook);

        // Get the hBook
        restHBookMockMvc.perform(get("/api/h-books/{id}", hBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hBook.getId().intValue()))
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
    public void getNonExistingHBook() throws Exception {
        // Get the hBook
        restHBookMockMvc.perform(get("/api/h-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHBook() throws Exception {
        // Initialize the database
        hBookRepository.saveAndFlush(hBook);
        int databaseSizeBeforeUpdate = hBookRepository.findAll().size();

        // Update the hBook
        HBook updatedHBook = hBookRepository.findOne(hBook.getId());
        updatedHBook
            .op(UPDATED_OP)
            .hi(UPDATED_HI)
            .lo(UPDATED_LO)
            .la(UPDATED_LA)
            .day(UPDATED_DAY)
            .vol(UPDATED_VOL)
            .pc(UPDATED_PC)
            .time(UPDATED_TIME);

        restHBookMockMvc.perform(put("/api/h-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHBook)))
            .andExpect(status().isOk());

        // Validate the HBook in the database
        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeUpdate);
        HBook testHBook = hBookList.get(hBookList.size() - 1);
        assertThat(testHBook.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testHBook.getHi()).isEqualTo(UPDATED_HI);
        assertThat(testHBook.getLo()).isEqualTo(UPDATED_LO);
        assertThat(testHBook.getLa()).isEqualTo(UPDATED_LA);
        assertThat(testHBook.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHBook.getVol()).isEqualTo(UPDATED_VOL);
        assertThat(testHBook.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testHBook.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingHBook() throws Exception {
        int databaseSizeBeforeUpdate = hBookRepository.findAll().size();

        // Create the HBook

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHBookMockMvc.perform(put("/api/h-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hBook)))
            .andExpect(status().isCreated());

        // Validate the HBook in the database
        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHBook() throws Exception {
        // Initialize the database
        hBookRepository.saveAndFlush(hBook);
        int databaseSizeBeforeDelete = hBookRepository.findAll().size();

        // Get the hBook
        restHBookMockMvc.perform(delete("/api/h-books/{id}", hBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HBook> hBookList = hBookRepository.findAll();
        assertThat(hBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HBook.class);
        HBook hBook1 = new HBook();
        hBook1.setId(1L);
        HBook hBook2 = new HBook();
        hBook2.setId(hBook1.getId());
        assertThat(hBook1).isEqualTo(hBook2);
        hBook2.setId(2L);
        assertThat(hBook1).isNotEqualTo(hBook2);
        hBook1.setId(null);
        assertThat(hBook1).isNotEqualTo(hBook2);
    }
}
