package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.AppUser;
import rocks.gebsattel.hochzeit.domain.WeddingGuest;
import rocks.gebsattel.hochzeit.domain.WeddingHost;
import rocks.gebsattel.hochzeit.domain.WeddingService;
import rocks.gebsattel.hochzeit.domain.PartyFood;
import rocks.gebsattel.hochzeit.domain.PartyEvent;
import rocks.gebsattel.hochzeit.domain.Message;
import rocks.gebsattel.hochzeit.repository.AppUserRepository;
import rocks.gebsattel.hochzeit.service.AppUserService;
import rocks.gebsattel.hochzeit.repository.search.AppUserSearchRepository;
import rocks.gebsattel.hochzeit.service.dto.AppUserDTO;
import rocks.gebsattel.hochzeit.service.mapper.AppUserMapper;
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

import rocks.gebsattel.hochzeit.domain.enumeration.Language;
/**
 * Test class for the AppUserResource REST controller.
 *
 * @see AppUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class AppUserResourceIntTest {

    private static final String DEFAULT_APP_USER_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_APP_USER_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_APP_USER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_APP_USER_PASSWORD = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.GERMAN;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserSearchRepository appUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppUserMockMvc;

    private AppUser appUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppUserResource appUserResource = new AppUserResource(appUserService);
        this.restAppUserMockMvc = MockMvcBuilders.standaloneSetup(appUserResource)
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
    public static AppUser createEntity(EntityManager em) {
        AppUser appUser = new AppUser()
            .appUserAccount(DEFAULT_APP_USER_ACCOUNT)
            .appUserPassword(DEFAULT_APP_USER_PASSWORD)
            .language(DEFAULT_LANGUAGE);
        // Add required entity
        WeddingGuest weddingGuest = WeddingGuestResourceIntTest.createEntity(em);
        em.persist(weddingGuest);
        em.flush();
        appUser.setWeddingGuest(weddingGuest);
        // Add required entity
        WeddingHost weddingHost = WeddingHostResourceIntTest.createEntity(em);
        em.persist(weddingHost);
        em.flush();
        appUser.setWeddingHost(weddingHost);
        // Add required entity
        WeddingService weddingService = WeddingServiceResourceIntTest.createEntity(em);
        em.persist(weddingService);
        em.flush();
        appUser.setWeddingService(weddingService);
        // Add required entity
        PartyFood foodOwner = PartyFoodResourceIntTest.createEntity(em);
        em.persist(foodOwner);
        em.flush();
        appUser.getFoodOwners().add(foodOwner);
        // Add required entity
        PartyEvent eventOwner = PartyEventResourceIntTest.createEntity(em);
        em.persist(eventOwner);
        em.flush();
        appUser.getEventOwners().add(eventOwner);
        // Add required entity
        Message messageOwner = MessageResourceIntTest.createEntity(em);
        em.persist(messageOwner);
        em.flush();
        appUser.getMessageOwners().add(messageOwner);
        return appUser;
    }

    @Before
    public void initTest() {
        appUserSearchRepository.deleteAll();
        appUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppUser() throws Exception {
        int databaseSizeBeforeCreate = appUserRepository.findAll().size();

        // Create the AppUser
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);
        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeCreate + 1);
        AppUser testAppUser = appUserList.get(appUserList.size() - 1);
        assertThat(testAppUser.getAppUserAccount()).isEqualTo(DEFAULT_APP_USER_ACCOUNT);
        assertThat(testAppUser.getAppUserPassword()).isEqualTo(DEFAULT_APP_USER_PASSWORD);
        assertThat(testAppUser.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the AppUser in Elasticsearch
        AppUser appUserEs = appUserSearchRepository.findOne(testAppUser.getId());
        assertThat(appUserEs).isEqualToIgnoringGivenFields(testAppUser);
    }

    @Test
    @Transactional
    public void createAppUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appUserRepository.findAll().size();

        // Create the AppUser with an existing ID
        appUser.setId(1L);
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAppUserAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserRepository.findAll().size();
        // set the field null
        appUser.setAppUserAccount(null);

        // Create the AppUser, which fails.
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        restAppUserMockMvc.perform(post("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isBadRequest());

        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppUsers() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);

        // Get all the appUserList
        restAppUserMockMvc.perform(get("/api/app-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].appUserAccount").value(hasItem(DEFAULT_APP_USER_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].appUserPassword").value(hasItem(DEFAULT_APP_USER_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);

        // Get the appUser
        restAppUserMockMvc.perform(get("/api/app-users/{id}", appUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appUser.getId().intValue()))
            .andExpect(jsonPath("$.appUserAccount").value(DEFAULT_APP_USER_ACCOUNT.toString()))
            .andExpect(jsonPath("$.appUserPassword").value(DEFAULT_APP_USER_PASSWORD.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppUser() throws Exception {
        // Get the appUser
        restAppUserMockMvc.perform(get("/api/app-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);
        appUserSearchRepository.save(appUser);
        int databaseSizeBeforeUpdate = appUserRepository.findAll().size();

        // Update the appUser
        AppUser updatedAppUser = appUserRepository.findOne(appUser.getId());
        // Disconnect from session so that the updates on updatedAppUser are not directly saved in db
        em.detach(updatedAppUser);
        updatedAppUser
            .appUserAccount(UPDATED_APP_USER_ACCOUNT)
            .appUserPassword(UPDATED_APP_USER_PASSWORD)
            .language(UPDATED_LANGUAGE);
        AppUserDTO appUserDTO = appUserMapper.toDto(updatedAppUser);

        restAppUserMockMvc.perform(put("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isOk());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeUpdate);
        AppUser testAppUser = appUserList.get(appUserList.size() - 1);
        assertThat(testAppUser.getAppUserAccount()).isEqualTo(UPDATED_APP_USER_ACCOUNT);
        assertThat(testAppUser.getAppUserPassword()).isEqualTo(UPDATED_APP_USER_PASSWORD);
        assertThat(testAppUser.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the AppUser in Elasticsearch
        AppUser appUserEs = appUserSearchRepository.findOne(testAppUser.getId());
        assertThat(appUserEs).isEqualToIgnoringGivenFields(testAppUser);
    }

    @Test
    @Transactional
    public void updateNonExistingAppUser() throws Exception {
        int databaseSizeBeforeUpdate = appUserRepository.findAll().size();

        // Create the AppUser
        AppUserDTO appUserDTO = appUserMapper.toDto(appUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppUserMockMvc.perform(put("/api/app-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AppUser in the database
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);
        appUserSearchRepository.save(appUser);
        int databaseSizeBeforeDelete = appUserRepository.findAll().size();

        // Get the appUser
        restAppUserMockMvc.perform(delete("/api/app-users/{id}", appUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean appUserExistsInEs = appUserSearchRepository.exists(appUser.getId());
        assertThat(appUserExistsInEs).isFalse();

        // Validate the database is empty
        List<AppUser> appUserList = appUserRepository.findAll();
        assertThat(appUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAppUser() throws Exception {
        // Initialize the database
        appUserRepository.saveAndFlush(appUser);
        appUserSearchRepository.save(appUser);

        // Search the appUser
        restAppUserMockMvc.perform(get("/api/_search/app-users?query=id:" + appUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].appUserAccount").value(hasItem(DEFAULT_APP_USER_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].appUserPassword").value(hasItem(DEFAULT_APP_USER_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = new AppUser();
        appUser1.setId(1L);
        AppUser appUser2 = new AppUser();
        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);
        appUser2.setId(2L);
        assertThat(appUser1).isNotEqualTo(appUser2);
        appUser1.setId(null);
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserDTO.class);
        AppUserDTO appUserDTO1 = new AppUserDTO();
        appUserDTO1.setId(1L);
        AppUserDTO appUserDTO2 = new AppUserDTO();
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
        appUserDTO2.setId(appUserDTO1.getId());
        assertThat(appUserDTO1).isEqualTo(appUserDTO2);
        appUserDTO2.setId(2L);
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
        appUserDTO1.setId(null);
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appUserMapper.fromId(null)).isNull();
    }
}
