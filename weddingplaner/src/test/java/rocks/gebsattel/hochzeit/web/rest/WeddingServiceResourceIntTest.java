package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.WeddingService;
import rocks.gebsattel.hochzeit.repository.WeddingServiceRepository;
import rocks.gebsattel.hochzeit.service.WeddingServiceService;
import rocks.gebsattel.hochzeit.repository.search.WeddingServiceSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.WeddingServiceDTO;
import rocks.gebsattel.hochzeit.service.mapper.WeddingServiceMapper;
import rocks.gebsattel.hochzeit.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeddingServiceResource REST controller.
 *
 * @see WeddingServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class WeddingServiceResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_SERVICE_COMMITTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICE_COMMITTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WeddingServiceRepository weddingServiceRepository;

    @Autowired
    private WeddingServiceMapper weddingServiceMapper;

    @Autowired
    private WeddingServiceService weddingServiceService;

    @Autowired
    private WeddingServiceSearchRepository weddingServiceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeddingServiceMockMvc;

    private WeddingService weddingService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeddingServiceResource weddingServiceResource = new WeddingServiceResource(weddingServiceService);
        this.restWeddingServiceMockMvc = MockMvcBuilders.standaloneSetup(weddingServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeddingService createEntity(EntityManager em) {
        WeddingService weddingService = new WeddingService()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .serviceCommittedDate(DEFAULT_SERVICE_COMMITTED_DATE);
        return weddingService;
    }

    @Before
    public void initTest() {
        weddingServiceSearchRepository.deleteAll();
        weddingService = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeddingService() throws Exception {
        int databaseSizeBeforeCreate = weddingServiceRepository.findAll().size();

        // Create the WeddingService
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(weddingService);
        restWeddingServiceMockMvc.perform(post("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the WeddingService in the database
        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeCreate + 1);
        WeddingService testWeddingService = weddingServiceList.get(weddingServiceList.size() - 1);
        assertThat(testWeddingService.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testWeddingService.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testWeddingService.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testWeddingService.getServiceCommittedDate()).isEqualTo(DEFAULT_SERVICE_COMMITTED_DATE);

        // Validate the WeddingService in Elasticsearch
        WeddingService weddingServiceEs = weddingServiceSearchRepository.findOne(testWeddingService.getId());
        assertThat(weddingServiceEs).isEqualToIgnoringGivenFields(testWeddingService);
    }

    @Test
    @Transactional
    public void createWeddingServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weddingServiceRepository.findAll().size();

        // Create the WeddingService with an existing ID
        weddingService.setId(1L);
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(weddingService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeddingServiceMockMvc.perform(post("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WeddingService in the database
        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingServiceRepository.findAll().size();
        // set the field null
        weddingService.setFirstName(null);

        // Create the WeddingService, which fails.
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(weddingService);

        restWeddingServiceMockMvc.perform(post("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingServiceRepository.findAll().size();
        // set the field null
        weddingService.setLastName(null);

        // Create the WeddingService, which fails.
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(weddingService);

        restWeddingServiceMockMvc.perform(post("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingServiceRepository.findAll().size();
        // set the field null
        weddingService.setEmail(null);

        // Create the WeddingService, which fails.
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(weddingService);

        restWeddingServiceMockMvc.perform(post("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeddingServices() throws Exception {
        // Initialize the database
        weddingServiceRepository.saveAndFlush(weddingService);

        // Get all the weddingServiceList
        restWeddingServiceMockMvc.perform(get("/api/wedding-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weddingService.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].serviceCommittedDate").value(hasItem(DEFAULT_SERVICE_COMMITTED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getWeddingService() throws Exception {
        // Initialize the database
        weddingServiceRepository.saveAndFlush(weddingService);

        // Get the weddingService
        restWeddingServiceMockMvc.perform(get("/api/wedding-services/{id}", weddingService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weddingService.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.serviceCommittedDate").value(DEFAULT_SERVICE_COMMITTED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeddingService() throws Exception {
        // Get the weddingService
        restWeddingServiceMockMvc.perform(get("/api/wedding-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeddingService() throws Exception {
        // Initialize the database
        weddingServiceRepository.saveAndFlush(weddingService);
        weddingServiceSearchRepository.save(weddingService);
        int databaseSizeBeforeUpdate = weddingServiceRepository.findAll().size();

        // Update the weddingService
        WeddingService updatedWeddingService = weddingServiceRepository.findOne(weddingService.getId());
        // Disconnect from session so that the updates on updatedWeddingService are not directly saved in db
        em.detach(updatedWeddingService);
        updatedWeddingService
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .serviceCommittedDate(UPDATED_SERVICE_COMMITTED_DATE);
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(updatedWeddingService);

        restWeddingServiceMockMvc.perform(put("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isOk());

        // Validate the WeddingService in the database
        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeUpdate);
        WeddingService testWeddingService = weddingServiceList.get(weddingServiceList.size() - 1);
        assertThat(testWeddingService.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testWeddingService.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testWeddingService.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testWeddingService.getServiceCommittedDate()).isEqualTo(UPDATED_SERVICE_COMMITTED_DATE);

        // Validate the WeddingService in Elasticsearch
        WeddingService weddingServiceEs = weddingServiceSearchRepository.findOne(testWeddingService.getId());
        assertThat(weddingServiceEs).isEqualToIgnoringGivenFields(testWeddingService);
    }

    @Test
    @Transactional
    public void updateNonExistingWeddingService() throws Exception {
        int databaseSizeBeforeUpdate = weddingServiceRepository.findAll().size();

        // Create the WeddingService
        WeddingServiceDTO weddingServiceDTO = weddingServiceMapper.toDto(weddingService);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWeddingServiceMockMvc.perform(put("/api/wedding-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the WeddingService in the database
        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWeddingService() throws Exception {
        // Initialize the database
        weddingServiceRepository.saveAndFlush(weddingService);
        weddingServiceSearchRepository.save(weddingService);
        int databaseSizeBeforeDelete = weddingServiceRepository.findAll().size();

        // Get the weddingService
        restWeddingServiceMockMvc.perform(delete("/api/wedding-services/{id}", weddingService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean weddingServiceExistsInEs = weddingServiceSearchRepository.exists(weddingService.getId());
        assertThat(weddingServiceExistsInEs).isFalse();

        // Validate the database is empty
        List<WeddingService> weddingServiceList = weddingServiceRepository.findAll();
        assertThat(weddingServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWeddingService() throws Exception {
        // Initialize the database
        weddingServiceRepository.saveAndFlush(weddingService);
        weddingServiceSearchRepository.save(weddingService);

        // Search the weddingService
        restWeddingServiceMockMvc.perform(get("/api/_search/wedding-services?query=id:" + weddingService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weddingService.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].serviceCommittedDate").value(hasItem(DEFAULT_SERVICE_COMMITTED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeddingService.class);
        WeddingService weddingService1 = new WeddingService();
        weddingService1.setId(1L);
        WeddingService weddingService2 = new WeddingService();
        weddingService2.setId(weddingService1.getId());
        assertThat(weddingService1).isEqualTo(weddingService2);
        weddingService2.setId(2L);
        assertThat(weddingService1).isNotEqualTo(weddingService2);
        weddingService1.setId(null);
        assertThat(weddingService1).isNotEqualTo(weddingService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeddingServiceDTO.class);
        WeddingServiceDTO weddingServiceDTO1 = new WeddingServiceDTO();
        weddingServiceDTO1.setId(1L);
        WeddingServiceDTO weddingServiceDTO2 = new WeddingServiceDTO();
        assertThat(weddingServiceDTO1).isNotEqualTo(weddingServiceDTO2);
        weddingServiceDTO2.setId(weddingServiceDTO1.getId());
        assertThat(weddingServiceDTO1).isEqualTo(weddingServiceDTO2);
        weddingServiceDTO2.setId(2L);
        assertThat(weddingServiceDTO1).isNotEqualTo(weddingServiceDTO2);
        weddingServiceDTO1.setId(null);
        assertThat(weddingServiceDTO1).isNotEqualTo(weddingServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(weddingServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(weddingServiceMapper.fromId(null)).isNull();
    }
}
