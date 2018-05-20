package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.PartyEvent;
import rocks.gebsattel.hochzeit.repository.PartyEventRepository;
import rocks.gebsattel.hochzeit.service.PartyEventService;
import rocks.gebsattel.hochzeit.repository.search.PartyEventSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.PartyEventDTO;
import rocks.gebsattel.hochzeit.service.mapper.PartyEventMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static rocks.gebsattel.hochzeit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import rocks.gebsattel.hochzeit.domain.enumeration.Language;
/**
 * Test class for the PartyEventResource REST controller.
 *
 * @see PartyEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class PartyEventResourceIntTest {

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_SHORT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EVENT_SHORT_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_EVENT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.GERMAN;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final Instant DEFAULT_EVENT_INIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENT_INIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_EVENT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EVENT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_END_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EVENT_MAX_PERSON = 1;
    private static final Integer UPDATED_EVENT_MAX_PERSON = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private PartyEventRepository partyEventRepository;

    @Autowired
    private PartyEventMapper partyEventMapper;

    @Autowired
    private PartyEventService partyEventService;

    @Autowired
    private PartyEventSearchRepository partyEventSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartyEventMockMvc;

    private PartyEvent partyEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyEventResource partyEventResource = new PartyEventResource(partyEventService);
        this.restPartyEventMockMvc = MockMvcBuilders.standaloneSetup(partyEventResource)
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
    public static PartyEvent createEntity(EntityManager em) {
        PartyEvent partyEvent = new PartyEvent()
            .eventName(DEFAULT_EVENT_NAME)
            .eventShortDescription(DEFAULT_EVENT_SHORT_DESCRIPTION)
            .eventLongDescription(DEFAULT_EVENT_LONG_DESCRIPTION)
            .language(DEFAULT_LANGUAGE)
            .eventInitTime(DEFAULT_EVENT_INIT_TIME)
            .eventStartTime(DEFAULT_EVENT_START_TIME)
            .eventEndTime(DEFAULT_EVENT_END_TIME)
            .eventMaxPerson(DEFAULT_EVENT_MAX_PERSON)
            .weight(DEFAULT_WEIGHT);
        return partyEvent;
    }

    @Before
    public void initTest() {
        partyEventSearchRepository.deleteAll();
        partyEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyEvent() throws Exception {
        int databaseSizeBeforeCreate = partyEventRepository.findAll().size();

        // Create the PartyEvent
        PartyEventDTO partyEventDTO = partyEventMapper.toDto(partyEvent);
        restPartyEventMockMvc.perform(post("/api/party-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyEventDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyEvent in the database
        List<PartyEvent> partyEventList = partyEventRepository.findAll();
        assertThat(partyEventList).hasSize(databaseSizeBeforeCreate + 1);
        PartyEvent testPartyEvent = partyEventList.get(partyEventList.size() - 1);
        assertThat(testPartyEvent.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testPartyEvent.getEventShortDescription()).isEqualTo(DEFAULT_EVENT_SHORT_DESCRIPTION);
        assertThat(testPartyEvent.getEventLongDescription()).isEqualTo(DEFAULT_EVENT_LONG_DESCRIPTION);
        assertThat(testPartyEvent.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testPartyEvent.getEventInitTime()).isEqualTo(DEFAULT_EVENT_INIT_TIME);
        assertThat(testPartyEvent.getEventStartTime()).isEqualTo(DEFAULT_EVENT_START_TIME);
        assertThat(testPartyEvent.getEventEndTime()).isEqualTo(DEFAULT_EVENT_END_TIME);
        assertThat(testPartyEvent.getEventMaxPerson()).isEqualTo(DEFAULT_EVENT_MAX_PERSON);
        assertThat(testPartyEvent.getWeight()).isEqualTo(DEFAULT_WEIGHT);

        // Validate the PartyEvent in Elasticsearch
        PartyEvent partyEventEs = partyEventSearchRepository.findOne(testPartyEvent.getId());
        assertThat(partyEventEs).isEqualToIgnoringGivenFields(testPartyEvent);
    }

    @Test
    @Transactional
    public void createPartyEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyEventRepository.findAll().size();

        // Create the PartyEvent with an existing ID
        partyEvent.setId(1L);
        PartyEventDTO partyEventDTO = partyEventMapper.toDto(partyEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyEventMockMvc.perform(post("/api/party-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartyEvent in the database
        List<PartyEvent> partyEventList = partyEventRepository.findAll();
        assertThat(partyEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEventNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyEventRepository.findAll().size();
        // set the field null
        partyEvent.setEventName(null);

        // Create the PartyEvent, which fails.
        PartyEventDTO partyEventDTO = partyEventMapper.toDto(partyEvent);

        restPartyEventMockMvc.perform(post("/api/party-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyEventDTO)))
            .andExpect(status().isBadRequest());

        List<PartyEvent> partyEventList = partyEventRepository.findAll();
        assertThat(partyEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyEvents() throws Exception {
        // Initialize the database
        partyEventRepository.saveAndFlush(partyEvent);

        // Get all the partyEventList
        restPartyEventMockMvc.perform(get("/api/party-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].eventShortDescription").value(hasItem(DEFAULT_EVENT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].eventLongDescription").value(hasItem(DEFAULT_EVENT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].eventInitTime").value(hasItem(DEFAULT_EVENT_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventStartTime").value(hasItem(DEFAULT_EVENT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventEndTime").value(hasItem(DEFAULT_EVENT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventMaxPerson").value(hasItem(DEFAULT_EVENT_MAX_PERSON)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    @Test
    @Transactional
    public void getPartyEvent() throws Exception {
        // Initialize the database
        partyEventRepository.saveAndFlush(partyEvent);

        // Get the partyEvent
        restPartyEventMockMvc.perform(get("/api/party-events/{id}", partyEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME.toString()))
            .andExpect(jsonPath("$.eventShortDescription").value(DEFAULT_EVENT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.eventLongDescription").value(DEFAULT_EVENT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.eventInitTime").value(DEFAULT_EVENT_INIT_TIME.toString()))
            .andExpect(jsonPath("$.eventStartTime").value(DEFAULT_EVENT_START_TIME.toString()))
            .andExpect(jsonPath("$.eventEndTime").value(DEFAULT_EVENT_END_TIME.toString()))
            .andExpect(jsonPath("$.eventMaxPerson").value(DEFAULT_EVENT_MAX_PERSON))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingPartyEvent() throws Exception {
        // Get the partyEvent
        restPartyEventMockMvc.perform(get("/api/party-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyEvent() throws Exception {
        // Initialize the database
        partyEventRepository.saveAndFlush(partyEvent);
        partyEventSearchRepository.save(partyEvent);
        int databaseSizeBeforeUpdate = partyEventRepository.findAll().size();

        // Update the partyEvent
        PartyEvent updatedPartyEvent = partyEventRepository.findOne(partyEvent.getId());
        // Disconnect from session so that the updates on updatedPartyEvent are not directly saved in db
        em.detach(updatedPartyEvent);
        updatedPartyEvent
            .eventName(UPDATED_EVENT_NAME)
            .eventShortDescription(UPDATED_EVENT_SHORT_DESCRIPTION)
            .eventLongDescription(UPDATED_EVENT_LONG_DESCRIPTION)
            .language(UPDATED_LANGUAGE)
            .eventInitTime(UPDATED_EVENT_INIT_TIME)
            .eventStartTime(UPDATED_EVENT_START_TIME)
            .eventEndTime(UPDATED_EVENT_END_TIME)
            .eventMaxPerson(UPDATED_EVENT_MAX_PERSON)
            .weight(UPDATED_WEIGHT);
        PartyEventDTO partyEventDTO = partyEventMapper.toDto(updatedPartyEvent);

        restPartyEventMockMvc.perform(put("/api/party-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyEventDTO)))
            .andExpect(status().isOk());

        // Validate the PartyEvent in the database
        List<PartyEvent> partyEventList = partyEventRepository.findAll();
        assertThat(partyEventList).hasSize(databaseSizeBeforeUpdate);
        PartyEvent testPartyEvent = partyEventList.get(partyEventList.size() - 1);
        assertThat(testPartyEvent.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testPartyEvent.getEventShortDescription()).isEqualTo(UPDATED_EVENT_SHORT_DESCRIPTION);
        assertThat(testPartyEvent.getEventLongDescription()).isEqualTo(UPDATED_EVENT_LONG_DESCRIPTION);
        assertThat(testPartyEvent.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testPartyEvent.getEventInitTime()).isEqualTo(UPDATED_EVENT_INIT_TIME);
        assertThat(testPartyEvent.getEventStartTime()).isEqualTo(UPDATED_EVENT_START_TIME);
        assertThat(testPartyEvent.getEventEndTime()).isEqualTo(UPDATED_EVENT_END_TIME);
        assertThat(testPartyEvent.getEventMaxPerson()).isEqualTo(UPDATED_EVENT_MAX_PERSON);
        assertThat(testPartyEvent.getWeight()).isEqualTo(UPDATED_WEIGHT);

        // Validate the PartyEvent in Elasticsearch
        PartyEvent partyEventEs = partyEventSearchRepository.findOne(testPartyEvent.getId());
        assertThat(partyEventEs).isEqualToIgnoringGivenFields(testPartyEvent);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyEvent() throws Exception {
        int databaseSizeBeforeUpdate = partyEventRepository.findAll().size();

        // Create the PartyEvent
        PartyEventDTO partyEventDTO = partyEventMapper.toDto(partyEvent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartyEventMockMvc.perform(put("/api/party-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyEventDTO)))
            .andExpect(status().isCreated());

        // Validate the PartyEvent in the database
        List<PartyEvent> partyEventList = partyEventRepository.findAll();
        assertThat(partyEventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePartyEvent() throws Exception {
        // Initialize the database
        partyEventRepository.saveAndFlush(partyEvent);
        partyEventSearchRepository.save(partyEvent);
        int databaseSizeBeforeDelete = partyEventRepository.findAll().size();

        // Get the partyEvent
        restPartyEventMockMvc.perform(delete("/api/party-events/{id}", partyEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean partyEventExistsInEs = partyEventSearchRepository.exists(partyEvent.getId());
        assertThat(partyEventExistsInEs).isFalse();

        // Validate the database is empty
        List<PartyEvent> partyEventList = partyEventRepository.findAll();
        assertThat(partyEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPartyEvent() throws Exception {
        // Initialize the database
        partyEventRepository.saveAndFlush(partyEvent);
        partyEventSearchRepository.save(partyEvent);

        // Search the partyEvent
        restPartyEventMockMvc.perform(get("/api/_search/party-events?query=id:" + partyEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].eventShortDescription").value(hasItem(DEFAULT_EVENT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].eventLongDescription").value(hasItem(DEFAULT_EVENT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].eventInitTime").value(hasItem(DEFAULT_EVENT_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventStartTime").value(hasItem(DEFAULT_EVENT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventEndTime").value(hasItem(DEFAULT_EVENT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventMaxPerson").value(hasItem(DEFAULT_EVENT_MAX_PERSON)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyEvent.class);
        PartyEvent partyEvent1 = new PartyEvent();
        partyEvent1.setId(1L);
        PartyEvent partyEvent2 = new PartyEvent();
        partyEvent2.setId(partyEvent1.getId());
        assertThat(partyEvent1).isEqualTo(partyEvent2);
        partyEvent2.setId(2L);
        assertThat(partyEvent1).isNotEqualTo(partyEvent2);
        partyEvent1.setId(null);
        assertThat(partyEvent1).isNotEqualTo(partyEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyEventDTO.class);
        PartyEventDTO partyEventDTO1 = new PartyEventDTO();
        partyEventDTO1.setId(1L);
        PartyEventDTO partyEventDTO2 = new PartyEventDTO();
        assertThat(partyEventDTO1).isNotEqualTo(partyEventDTO2);
        partyEventDTO2.setId(partyEventDTO1.getId());
        assertThat(partyEventDTO1).isEqualTo(partyEventDTO2);
        partyEventDTO2.setId(2L);
        assertThat(partyEventDTO1).isNotEqualTo(partyEventDTO2);
        partyEventDTO1.setId(null);
        assertThat(partyEventDTO1).isNotEqualTo(partyEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partyEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partyEventMapper.fromId(null)).isNull();
    }
}
