package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpalitApp;

import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Asset;
import com.mycompany.myapp.repository.TransactionRepository;
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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransactionResource REST controller.
 *
 * @see TransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpalitApp.class)
public class TransactionResourceIntTest {

    private static final Integer DEFAULT_ID_POSITION = 1;
    private static final Integer UPDATED_ID_POSITION = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_TRADE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRADE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SETTLEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SETTLEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_MARKET_FEES = 1D;
    private static final Double UPDATED_MARKET_FEES = 2D;

    private static final Double DEFAULT_BROKER_FEES = 1D;
    private static final Double UPDATED_BROKER_FEES = 2D;

    private static final Float DEFAULT_CLIENT_FEES = 1F;
    private static final Float UPDATED_CLIENT_FEES = 2F;

    private static final Double DEFAULT_FOREX_SPOT = 1D;
    private static final Double UPDATED_FOREX_SPOT = 2D;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_BO_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_BO_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CFD = false;
    private static final Boolean UPDATED_CFD = true;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionResource transactionResource = new TransactionResource(transactionRepository);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
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
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .idPosition(DEFAULT_ID_POSITION)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .tradeDate(DEFAULT_TRADE_DATE)
            .settlementDate(DEFAULT_SETTLEMENT_DATE)
            .amount(DEFAULT_AMOUNT)
            .marketFees(DEFAULT_MARKET_FEES)
            .brokerFees(DEFAULT_BROKER_FEES)
            .clientFees(DEFAULT_CLIENT_FEES)
            .forexSpot(DEFAULT_FOREX_SPOT)
            .comments(DEFAULT_COMMENTS)
            .boComment(DEFAULT_BO_COMMENT)
            .cfd(DEFAULT_CFD);
        // Add required entity
        Book book = BookResourceIntTest.createEntity(em);
        em.persist(book);
        em.flush();
        transaction.setBook(book);
        // Add required entity
        Asset asset = AssetResourceIntTest.createEntity(em);
        em.persist(asset);
        em.flush();
        transaction.setAsset(asset);
        return transaction;
    }

    @Before
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getIdPosition()).isEqualTo(DEFAULT_ID_POSITION);
        assertThat(testTransaction.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTransaction.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTransaction.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransaction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransaction.getTradeDate()).isEqualTo(DEFAULT_TRADE_DATE);
        assertThat(testTransaction.getSettlementDate()).isEqualTo(DEFAULT_SETTLEMENT_DATE);
        assertThat(testTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransaction.getMarketFees()).isEqualTo(DEFAULT_MARKET_FEES);
        assertThat(testTransaction.getBrokerFees()).isEqualTo(DEFAULT_BROKER_FEES);
        assertThat(testTransaction.getClientFees()).isEqualTo(DEFAULT_CLIENT_FEES);
        assertThat(testTransaction.getForexSpot()).isEqualTo(DEFAULT_FOREX_SPOT);
        assertThat(testTransaction.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTransaction.getBoComment()).isEqualTo(DEFAULT_BO_COMMENT);
        assertThat(testTransaction.isCfd()).isEqualTo(DEFAULT_CFD);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setType(null);

        // Create the Transaction, which fails.

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setStatus(null);

        // Create the Transaction, which fails.

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTradeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setTradeDate(null);

        // Create the Transaction, which fails.

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSettlementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setSettlementDate(null);

        // Create the Transaction, which fails.

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPosition").value(hasItem(DEFAULT_ID_POSITION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].tradeDate").value(hasItem(DEFAULT_TRADE_DATE.toString())))
            .andExpect(jsonPath("$.[*].settlementDate").value(hasItem(DEFAULT_SETTLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].marketFees").value(hasItem(DEFAULT_MARKET_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].brokerFees").value(hasItem(DEFAULT_BROKER_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].clientFees").value(hasItem(DEFAULT_CLIENT_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].forexSpot").value(hasItem(DEFAULT_FOREX_SPOT.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].boComment").value(hasItem(DEFAULT_BO_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].cfd").value(hasItem(DEFAULT_CFD.booleanValue())));
    }

    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.idPosition").value(DEFAULT_ID_POSITION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.tradeDate").value(DEFAULT_TRADE_DATE.toString()))
            .andExpect(jsonPath("$.settlementDate").value(DEFAULT_SETTLEMENT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.marketFees").value(DEFAULT_MARKET_FEES.doubleValue()))
            .andExpect(jsonPath("$.brokerFees").value(DEFAULT_BROKER_FEES.doubleValue()))
            .andExpect(jsonPath("$.clientFees").value(DEFAULT_CLIENT_FEES.doubleValue()))
            .andExpect(jsonPath("$.forexSpot").value(DEFAULT_FOREX_SPOT.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.boComment").value(DEFAULT_BO_COMMENT.toString()))
            .andExpect(jsonPath("$.cfd").value(DEFAULT_CFD.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findOne(transaction.getId());
        updatedTransaction
            .idPosition(UPDATED_ID_POSITION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .tradeDate(UPDATED_TRADE_DATE)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .marketFees(UPDATED_MARKET_FEES)
            .brokerFees(UPDATED_BROKER_FEES)
            .clientFees(UPDATED_CLIENT_FEES)
            .forexSpot(UPDATED_FOREX_SPOT)
            .comments(UPDATED_COMMENTS)
            .boComment(UPDATED_BO_COMMENT)
            .cfd(UPDATED_CFD);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getIdPosition()).isEqualTo(UPDATED_ID_POSITION);
        assertThat(testTransaction.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransaction.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTransaction.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransaction.getTradeDate()).isEqualTo(UPDATED_TRADE_DATE);
        assertThat(testTransaction.getSettlementDate()).isEqualTo(UPDATED_SETTLEMENT_DATE);
        assertThat(testTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaction.getMarketFees()).isEqualTo(UPDATED_MARKET_FEES);
        assertThat(testTransaction.getBrokerFees()).isEqualTo(UPDATED_BROKER_FEES);
        assertThat(testTransaction.getClientFees()).isEqualTo(UPDATED_CLIENT_FEES);
        assertThat(testTransaction.getForexSpot()).isEqualTo(UPDATED_FOREX_SPOT);
        assertThat(testTransaction.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTransaction.getBoComment()).isEqualTo(UPDATED_BO_COMMENT);
        assertThat(testTransaction.isCfd()).isEqualTo(UPDATED_CFD);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
