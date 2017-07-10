package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.Asset;
import com.mycompany.myapp.repository.AssetRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssetResource REST controller.
 *
 * @see AssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class AssetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Double DEFAULT_QUOTITY = 1D;
    private static final Double UPDATED_QUOTITY = 2D;

    private static final byte[] DEFAULT_COMMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENTS = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COMMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENTS_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_GDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_GNUMBERA = 1;
    private static final Integer UPDATED_GNUMBERA = 2;

    private static final Integer DEFAULT_GENUMA = 1;
    private static final Integer UPDATED_GENUMA = 2;

    private static final Integer DEFAULT_GNUMBERB = 1;
    private static final Integer UPDATED_GNUMBERB = 2;

    private static final LocalDate DEFAULT_GDATEB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GDATEB = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_GENUMB = 1;
    private static final Integer UPDATED_GENUMB = 2;

    private static final Integer DEFAULT_GENUMC = 1;
    private static final Integer UPDATED_GENUMC = 2;

    private static final Integer DEFAULT_GENUMD = 1;
    private static final Integer UPDATED_GENUMD = 2;

    private static final LocalDate DEFAULT_GDATEC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GDATEC = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetMockMvc;

    private Asset asset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetResource assetResource = new AssetResource(assetRepository);
        this.restAssetMockMvc = MockMvcBuilders.standaloneSetup(assetResource)
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
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .name(DEFAULT_NAME)
            .reference(DEFAULT_REFERENCE)
            .type(DEFAULT_TYPE)
            .quotity(DEFAULT_QUOTITY)
            .comments(DEFAULT_COMMENTS)
            .commentsContentType(DEFAULT_COMMENTS_CONTENT_TYPE)
            .gdate(DEFAULT_GDATE)
            .gnumbera(DEFAULT_GNUMBERA)
            .genuma(DEFAULT_GENUMA)
            .gnumberb(DEFAULT_GNUMBERB)
            .gdateb(DEFAULT_GDATEB)
            .genumb(DEFAULT_GENUMB)
            .genumc(DEFAULT_GENUMC)
            .genumd(DEFAULT_GENUMD)
            .gdatec(DEFAULT_GDATEC);
        return asset;
    }

    @Before
    public void initTest() {
        asset = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsset() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate + 1);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAsset.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testAsset.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAsset.getQuotity()).isEqualTo(DEFAULT_QUOTITY);
        assertThat(testAsset.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAsset.getCommentsContentType()).isEqualTo(DEFAULT_COMMENTS_CONTENT_TYPE);
        assertThat(testAsset.getGdate()).isEqualTo(DEFAULT_GDATE);
        assertThat(testAsset.getGnumbera()).isEqualTo(DEFAULT_GNUMBERA);
        assertThat(testAsset.getGenuma()).isEqualTo(DEFAULT_GENUMA);
        assertThat(testAsset.getGnumberb()).isEqualTo(DEFAULT_GNUMBERB);
        assertThat(testAsset.getGdateb()).isEqualTo(DEFAULT_GDATEB);
        assertThat(testAsset.getGenumb()).isEqualTo(DEFAULT_GENUMB);
        assertThat(testAsset.getGenumc()).isEqualTo(DEFAULT_GENUMC);
        assertThat(testAsset.getGenumd()).isEqualTo(DEFAULT_GENUMD);
        assertThat(testAsset.getGdatec()).isEqualTo(DEFAULT_GDATEC);
    }

    @Test
    @Transactional
    public void createAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset with an existing ID
        asset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssets() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].quotity").value(hasItem(DEFAULT_QUOTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].commentsContentType").value(hasItem(DEFAULT_COMMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENTS))))
            .andExpect(jsonPath("$.[*].gdate").value(hasItem(DEFAULT_GDATE.toString())))
            .andExpect(jsonPath("$.[*].gnumbera").value(hasItem(DEFAULT_GNUMBERA)))
            .andExpect(jsonPath("$.[*].genuma").value(hasItem(DEFAULT_GENUMA)))
            .andExpect(jsonPath("$.[*].gnumberb").value(hasItem(DEFAULT_GNUMBERB)))
            .andExpect(jsonPath("$.[*].gdateb").value(hasItem(DEFAULT_GDATEB.toString())))
            .andExpect(jsonPath("$.[*].genumb").value(hasItem(DEFAULT_GENUMB)))
            .andExpect(jsonPath("$.[*].genumc").value(hasItem(DEFAULT_GENUMC)))
            .andExpect(jsonPath("$.[*].genumd").value(hasItem(DEFAULT_GENUMD)))
            .andExpect(jsonPath("$.[*].gdatec").value(hasItem(DEFAULT_GDATEC.toString())));
    }

    @Test
    @Transactional
    public void getAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.quotity").value(DEFAULT_QUOTITY.doubleValue()))
            .andExpect(jsonPath("$.commentsContentType").value(DEFAULT_COMMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.comments").value(Base64Utils.encodeToString(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.gdate").value(DEFAULT_GDATE.toString()))
            .andExpect(jsonPath("$.gnumbera").value(DEFAULT_GNUMBERA))
            .andExpect(jsonPath("$.genuma").value(DEFAULT_GENUMA))
            .andExpect(jsonPath("$.gnumberb").value(DEFAULT_GNUMBERB))
            .andExpect(jsonPath("$.gdateb").value(DEFAULT_GDATEB.toString()))
            .andExpect(jsonPath("$.genumb").value(DEFAULT_GENUMB))
            .andExpect(jsonPath("$.genumc").value(DEFAULT_GENUMC))
            .andExpect(jsonPath("$.genumd").value(DEFAULT_GENUMD))
            .andExpect(jsonPath("$.gdatec").value(DEFAULT_GDATEC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset
        Asset updatedAsset = assetRepository.findOne(asset.getId());
        updatedAsset
            .name(UPDATED_NAME)
            .reference(UPDATED_REFERENCE)
            .type(UPDATED_TYPE)
            .quotity(UPDATED_QUOTITY)
            .comments(UPDATED_COMMENTS)
            .commentsContentType(UPDATED_COMMENTS_CONTENT_TYPE)
            .gdate(UPDATED_GDATE)
            .gnumbera(UPDATED_GNUMBERA)
            .genuma(UPDATED_GENUMA)
            .gnumberb(UPDATED_GNUMBERB)
            .gdateb(UPDATED_GDATEB)
            .genumb(UPDATED_GENUMB)
            .genumc(UPDATED_GENUMC)
            .genumd(UPDATED_GENUMD)
            .gdatec(UPDATED_GDATEC);

        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsset)))
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAsset.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testAsset.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAsset.getQuotity()).isEqualTo(UPDATED_QUOTITY);
        assertThat(testAsset.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAsset.getCommentsContentType()).isEqualTo(UPDATED_COMMENTS_CONTENT_TYPE);
        assertThat(testAsset.getGdate()).isEqualTo(UPDATED_GDATE);
        assertThat(testAsset.getGnumbera()).isEqualTo(UPDATED_GNUMBERA);
        assertThat(testAsset.getGenuma()).isEqualTo(UPDATED_GENUMA);
        assertThat(testAsset.getGnumberb()).isEqualTo(UPDATED_GNUMBERB);
        assertThat(testAsset.getGdateb()).isEqualTo(UPDATED_GDATEB);
        assertThat(testAsset.getGenumb()).isEqualTo(UPDATED_GENUMB);
        assertThat(testAsset.getGenumc()).isEqualTo(UPDATED_GENUMC);
        assertThat(testAsset.getGenumd()).isEqualTo(UPDATED_GENUMD);
        assertThat(testAsset.getGdatec()).isEqualTo(UPDATED_GDATEC);
    }

    @Test
    @Transactional
    public void updateNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Create the Asset

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);
        int databaseSizeBeforeDelete = assetRepository.findAll().size();

        // Get the asset
        restAssetMockMvc.perform(delete("/api/assets/{id}", asset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
        Asset asset1 = new Asset();
        asset1.setId(1L);
        Asset asset2 = new Asset();
        asset2.setId(asset1.getId());
        assertThat(asset1).isEqualTo(asset2);
        asset2.setId(2L);
        assertThat(asset1).isNotEqualTo(asset2);
        asset1.setId(null);
        assertThat(asset1).isNotEqualTo(asset2);
    }
}
