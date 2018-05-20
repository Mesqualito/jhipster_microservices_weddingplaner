package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.WeddingGuest;
import rocks.gebsattel.hochzeit.repository.WeddingGuestRepository;
import rocks.gebsattel.hochzeit.service.WeddingGuestService;
import rocks.gebsattel.hochzeit.repository.search.WeddingGuestSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.WeddingGuestDTO;
import rocks.gebsattel.hochzeit.service.mapper.WeddingGuestMapper;
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
 * Test class for the WeddingGuestResource REST controller.
 *
 * @see WeddingGuestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class WeddingGuestResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_GUEST_INVITATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GUEST_INVITATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_GUEST_COMMITTED = false;
    private static final Boolean UPDATED_GUEST_COMMITTED = true;

    private static final Instant DEFAULT_GUEST_RSVP_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GUEST_RSVP_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WeddingGuestRepository weddingGuestRepository;

    @Autowired
    private WeddingGuestMapper weddingGuestMapper;

    @Autowired
    private WeddingGuestService weddingGuestService;

    @Autowired
    private WeddingGuestSearchRepository weddingGuestSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeddingGuestMockMvc;

    private WeddingGuest weddingGuest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeddingGuestResource weddingGuestResource = new WeddingGuestResource(weddingGuestService);
        this.restWeddingGuestMockMvc = MockMvcBuilders.standaloneSetup(weddingGuestResource)
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
    public static WeddingGuest createEntity(EntityManager em) {
        WeddingGuest weddingGuest = new WeddingGuest()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .guestInvitationDate(DEFAULT_GUEST_INVITATION_DATE)
            .guestCommitted(DEFAULT_GUEST_COMMITTED)
            .guestRsvpDate(DEFAULT_GUEST_RSVP_DATE);
        return weddingGuest;
    }

    @Before
    public void initTest() {
        weddingGuestSearchRepository.deleteAll();
        weddingGuest = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeddingGuest() throws Exception {
        int databaseSizeBeforeCreate = weddingGuestRepository.findAll().size();

        // Create the WeddingGuest
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(weddingGuest);
        restWeddingGuestMockMvc.perform(post("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isCreated());

        // Validate the WeddingGuest in the database
        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeCreate + 1);
        WeddingGuest testWeddingGuest = weddingGuestList.get(weddingGuestList.size() - 1);
        assertThat(testWeddingGuest.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testWeddingGuest.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testWeddingGuest.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testWeddingGuest.getGuestInvitationDate()).isEqualTo(DEFAULT_GUEST_INVITATION_DATE);
        assertThat(testWeddingGuest.isGuestCommitted()).isEqualTo(DEFAULT_GUEST_COMMITTED);
        assertThat(testWeddingGuest.getGuestRsvpDate()).isEqualTo(DEFAULT_GUEST_RSVP_DATE);

        // Validate the WeddingGuest in Elasticsearch
        WeddingGuest weddingGuestEs = weddingGuestSearchRepository.findOne(testWeddingGuest.getId());
        assertThat(weddingGuestEs).isEqualToIgnoringGivenFields(testWeddingGuest);
    }

    @Test
    @Transactional
    public void createWeddingGuestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weddingGuestRepository.findAll().size();

        // Create the WeddingGuest with an existing ID
        weddingGuest.setId(1L);
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(weddingGuest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeddingGuestMockMvc.perform(post("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WeddingGuest in the database
        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingGuestRepository.findAll().size();
        // set the field null
        weddingGuest.setFirstName(null);

        // Create the WeddingGuest, which fails.
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(weddingGuest);

        restWeddingGuestMockMvc.perform(post("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingGuestRepository.findAll().size();
        // set the field null
        weddingGuest.setLastName(null);

        // Create the WeddingGuest, which fails.
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(weddingGuest);

        restWeddingGuestMockMvc.perform(post("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = weddingGuestRepository.findAll().size();
        // set the field null
        weddingGuest.setEmail(null);

        // Create the WeddingGuest, which fails.
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(weddingGuest);

        restWeddingGuestMockMvc.perform(post("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isBadRequest());

        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeddingGuests() throws Exception {
        // Initialize the database
        weddingGuestRepository.saveAndFlush(weddingGuest);

        // Get all the weddingGuestList
        restWeddingGuestMockMvc.perform(get("/api/wedding-guests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weddingGuest.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].guestInvitationDate").value(hasItem(DEFAULT_GUEST_INVITATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].guestCommitted").value(hasItem(DEFAULT_GUEST_COMMITTED.booleanValue())))
            .andExpect(jsonPath("$.[*].guestRsvpDate").value(hasItem(DEFAULT_GUEST_RSVP_DATE.toString())));
    }

    @Test
    @Transactional
    public void getWeddingGuest() throws Exception {
        // Initialize the database
        weddingGuestRepository.saveAndFlush(weddingGuest);

        // Get the weddingGuest
        restWeddingGuestMockMvc.perform(get("/api/wedding-guests/{id}", weddingGuest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weddingGuest.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.guestInvitationDate").value(DEFAULT_GUEST_INVITATION_DATE.toString()))
            .andExpect(jsonPath("$.guestCommitted").value(DEFAULT_GUEST_COMMITTED.booleanValue()))
            .andExpect(jsonPath("$.guestRsvpDate").value(DEFAULT_GUEST_RSVP_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeddingGuest() throws Exception {
        // Get the weddingGuest
        restWeddingGuestMockMvc.perform(get("/api/wedding-guests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeddingGuest() throws Exception {
        // Initialize the database
        weddingGuestRepository.saveAndFlush(weddingGuest);
        weddingGuestSearchRepository.save(weddingGuest);
        int databaseSizeBeforeUpdate = weddingGuestRepository.findAll().size();

        // Update the weddingGuest
        WeddingGuest updatedWeddingGuest = weddingGuestRepository.findOne(weddingGuest.getId());
        // Disconnect from session so that the updates on updatedWeddingGuest are not directly saved in db
        em.detach(updatedWeddingGuest);
        updatedWeddingGuest
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .guestInvitationDate(UPDATED_GUEST_INVITATION_DATE)
            .guestCommitted(UPDATED_GUEST_COMMITTED)
            .guestRsvpDate(UPDATED_GUEST_RSVP_DATE);
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(updatedWeddingGuest);

        restWeddingGuestMockMvc.perform(put("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isOk());

        // Validate the WeddingGuest in the database
        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeUpdate);
        WeddingGuest testWeddingGuest = weddingGuestList.get(weddingGuestList.size() - 1);
        assertThat(testWeddingGuest.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testWeddingGuest.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testWeddingGuest.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testWeddingGuest.getGuestInvitationDate()).isEqualTo(UPDATED_GUEST_INVITATION_DATE);
        assertThat(testWeddingGuest.isGuestCommitted()).isEqualTo(UPDATED_GUEST_COMMITTED);
        assertThat(testWeddingGuest.getGuestRsvpDate()).isEqualTo(UPDATED_GUEST_RSVP_DATE);

        // Validate the WeddingGuest in Elasticsearch
        WeddingGuest weddingGuestEs = weddingGuestSearchRepository.findOne(testWeddingGuest.getId());
        assertThat(weddingGuestEs).isEqualToIgnoringGivenFields(testWeddingGuest);
    }

    @Test
    @Transactional
    public void updateNonExistingWeddingGuest() throws Exception {
        int databaseSizeBeforeUpdate = weddingGuestRepository.findAll().size();

        // Create the WeddingGuest
        WeddingGuestDTO weddingGuestDTO = weddingGuestMapper.toDto(weddingGuest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWeddingGuestMockMvc.perform(put("/api/wedding-guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weddingGuestDTO)))
            .andExpect(status().isCreated());

        // Validate the WeddingGuest in the database
        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWeddingGuest() throws Exception {
        // Initialize the database
        weddingGuestRepository.saveAndFlush(weddingGuest);
        weddingGuestSearchRepository.save(weddingGuest);
        int databaseSizeBeforeDelete = weddingGuestRepository.findAll().size();

        // Get the weddingGuest
        restWeddingGuestMockMvc.perform(delete("/api/wedding-guests/{id}", weddingGuest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean weddingGuestExistsInEs = weddingGuestSearchRepository.exists(weddingGuest.getId());
        assertThat(weddingGuestExistsInEs).isFalse();

        // Validate the database is empty
        List<WeddingGuest> weddingGuestList = weddingGuestRepository.findAll();
        assertThat(weddingGuestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWeddingGuest() throws Exception {
        // Initialize the database
        weddingGuestRepository.saveAndFlush(weddingGuest);
        weddingGuestSearchRepository.save(weddingGuest);

        // Search the weddingGuest
        restWeddingGuestMockMvc.perform(get("/api/_search/wedding-guests?query=id:" + weddingGuest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weddingGuest.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].guestInvitationDate").value(hasItem(DEFAULT_GUEST_INVITATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].guestCommitted").value(hasItem(DEFAULT_GUEST_COMMITTED.booleanValue())))
            .andExpect(jsonPath("$.[*].guestRsvpDate").value(hasItem(DEFAULT_GUEST_RSVP_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeddingGuest.class);
        WeddingGuest weddingGuest1 = new WeddingGuest();
        weddingGuest1.setId(1L);
        WeddingGuest weddingGuest2 = new WeddingGuest();
        weddingGuest2.setId(weddingGuest1.getId());
        assertThat(weddingGuest1).isEqualTo(weddingGuest2);
        weddingGuest2.setId(2L);
        assertThat(weddingGuest1).isNotEqualTo(weddingGuest2);
        weddingGuest1.setId(null);
        assertThat(weddingGuest1).isNotEqualTo(weddingGuest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeddingGuestDTO.class);
        WeddingGuestDTO weddingGuestDTO1 = new WeddingGuestDTO();
        weddingGuestDTO1.setId(1L);
        WeddingGuestDTO weddingGuestDTO2 = new WeddingGuestDTO();
        assertThat(weddingGuestDTO1).isNotEqualTo(weddingGuestDTO2);
        weddingGuestDTO2.setId(weddingGuestDTO1.getId());
        assertThat(weddingGuestDTO1).isEqualTo(weddingGuestDTO2);
        weddingGuestDTO2.setId(2L);
        assertThat(weddingGuestDTO1).isNotEqualTo(weddingGuestDTO2);
        weddingGuestDTO1.setId(null);
        assertThat(weddingGuestDTO1).isNotEqualTo(weddingGuestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(weddingGuestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(weddingGuestMapper.fromId(null)).isNull();
    }
}
