package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.WeddingHost;
import rocks.gebsattel.hochzeit.repository.WeddingHostRepository;
import rocks.gebsattel.hochzeit.service.WeddingHostService;
import rocks.gebsattel.hochzeit.service.dto.WeddingHostDTO;
import rocks.gebsattel.hochzeit.service.mapper.WeddingHostMapper;
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
import java.util.List;

import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeddingHostResource REST controller.
 *
 * @see WeddingHostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class WeddingHostResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private WeddingHostRepository weddingHostRepository;

    @Autowired
    private WeddingHostMapper weddingHostMapper;

    @Autowired
    private WeddingHostService weddingHostService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeddingHostMockMvc;

    private WeddingHost weddingHost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeddingHostResource weddingHostResource = new WeddingHostResource(weddingHostService);
        this.restWeddingHostMockMvc = MockMvcBuilders.standaloneSetup(weddingHostResource)
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
    public static WeddingHost createEntity(EntityManager em) {
        WeddingHost weddingHost = new WeddingHost()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL);
        return weddingHost;
    }

    @Before
    public void initTest() {
        weddingHost = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeddingHost() throws Exception {
        int databaseSizeBeforeCreate = weddingHostRepository.findAll().size();

        // Create the WeddingHost
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(weddingHost);
        restWeddingHostMockMvc.perform(post("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isCreated());

        // Validate the WeddingHost in the database
        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeCreate + 1);
        WeddingHost testWeddingHost = weddingHostList.get(weddingHostList.size() - 1);
        assertThat(testWeddingHost.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testWeddingHost.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testWeddingHost.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createWeddingHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weddingHostRepository.findAll().size();

        // Create the WeddingHost with an existing ID
        weddingHost.setId(1L);
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(weddingHost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeddingHostMockMvc.perform(post("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WeddingHost in the database
        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingHostRepository.findAll().size();
        // set the field null
        weddingHost.setFirstName(null);

        // Create the WeddingHost, which fails.
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(weddingHost);

        restWeddingHostMockMvc.perform(post("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingHostRepository.findAll().size();
        // set the field null
        weddingHost.setLastName(null);

        // Create the WeddingHost, which fails.
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(weddingHost);

        restWeddingHostMockMvc.perform(post("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingHostRepository.findAll().size();
        // set the field null
        weddingHost.setEmail(null);

        // Create the WeddingHost, which fails.
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(weddingHost);

        restWeddingHostMockMvc.perform(post("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeddingHosts() throws Exception {
        // Initialize the database
        weddingHostRepository.saveAndFlush(weddingHost);

        // Get all the weddingHostList
        restWeddingHostMockMvc.perform(get("/api/wedding-hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weddingHost.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getWeddingHost() throws Exception {
        // Initialize the database
        weddingHostRepository.saveAndFlush(weddingHost);

        // Get the weddingHost
        restWeddingHostMockMvc.perform(get("/api/wedding-hosts/{id}", weddingHost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weddingHost.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeddingHost() throws Exception {
        // Get the weddingHost
        restWeddingHostMockMvc.perform(get("/api/wedding-hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeddingHost() throws Exception {
        // Initialize the database
        weddingHostRepository.saveAndFlush(weddingHost);
        int databaseSizeBeforeUpdate = weddingHostRepository.findAll().size();

        // Update the weddingHost
        WeddingHost updatedWeddingHost = weddingHostRepository.findOne(weddingHost.getId());
        // Disconnect from session so that the updates on updatedWeddingHost are not directly saved in db
        em.detach(updatedWeddingHost);
        updatedWeddingHost
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL);
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(updatedWeddingHost);

        restWeddingHostMockMvc.perform(put("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isOk());

        // Validate the WeddingHost in the database
        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeUpdate);
        WeddingHost testWeddingHost = weddingHostList.get(weddingHostList.size() - 1);
        assertThat(testWeddingHost.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testWeddingHost.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testWeddingHost.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingWeddingHost() throws Exception {
        int databaseSizeBeforeUpdate = weddingHostRepository.findAll().size();

        // Create the WeddingHost
        WeddingHostDTO weddingHostDTO = weddingHostMapper.toDto(weddingHost);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWeddingHostMockMvc.perform(put("/api/wedding-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingHostDTO)))
            .andExpect(status().isCreated());

        // Validate the WeddingHost in the database
        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWeddingHost() throws Exception {
        // Initialize the database
        weddingHostRepository.saveAndFlush(weddingHost);
        int databaseSizeBeforeDelete = weddingHostRepository.findAll().size();

        // Get the weddingHost
        restWeddingHostMockMvc.perform(delete("/api/wedding-hosts/{id}", weddingHost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WeddingHost> weddingHostList = weddingHostRepository.findAll();
        assertThat(weddingHostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeddingHost.class);
        WeddingHost weddingHost1 = new WeddingHost();
        weddingHost1.setId(1L);
        WeddingHost weddingHost2 = new WeddingHost();
        weddingHost2.setId(weddingHost1.getId());
        assertThat(weddingHost1).isEqualTo(weddingHost2);
        weddingHost2.setId(2L);
        assertThat(weddingHost1).isNotEqualTo(weddingHost2);
        weddingHost1.setId(null);
        assertThat(weddingHost1).isNotEqualTo(weddingHost2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeddingHostDTO.class);
        WeddingHostDTO weddingHostDTO1 = new WeddingHostDTO();
        weddingHostDTO1.setId(1L);
        WeddingHostDTO weddingHostDTO2 = new WeddingHostDTO();
        assertThat(weddingHostDTO1).isNotEqualTo(weddingHostDTO2);
        weddingHostDTO2.setId(weddingHostDTO1.getId());
        assertThat(weddingHostDTO1).isEqualTo(weddingHostDTO2);
        weddingHostDTO2.setId(2L);
        assertThat(weddingHostDTO1).isNotEqualTo(weddingHostDTO2);
        weddingHostDTO1.setId(null);
        assertThat(weddingHostDTO1).isNotEqualTo(weddingHostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(weddingHostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(weddingHostMapper.fromId(null)).isNull();
    }
}
