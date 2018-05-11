package rocks.gebsattel.hochzeit.web.rest;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import rocks.gebsattel.hochzeit.domain.Message;
import rocks.gebsattel.hochzeit.domain.AppUser;
import rocks.gebsattel.hochzeit.repository.MessageRepository;
import rocks.gebsattel.hochzeit.service.MessageService;
import rocks.gebsattel.hochzeit.service.dto.MessageDTO;
import rocks.gebsattel.hochzeit.service.mapper.MessageMapper;
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
 * Test class for the MessageResource REST controller.
 *
 * @see MessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingplanerApp.class)
public class MessageResourceIntTest {

    private static final String DEFAULT_MESSAGE_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_MESSAGE_INIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MESSAGE_INIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MESSAGE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_TEXT = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.GERMAN;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final LocalDate DEFAULT_MESSAGE_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MESSAGE_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MESSAGE_VALID_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MESSAGE_VALID_UNTIL = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageMockMvc;

    private Message message;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageResource messageResource = new MessageResource(messageService);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
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
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .messageShortDescription(DEFAULT_MESSAGE_SHORT_DESCRIPTION)
            .messageInitTime(DEFAULT_MESSAGE_INIT_TIME)
            .messageText(DEFAULT_MESSAGE_TEXT)
            .language(DEFAULT_LANGUAGE)
            .messageValidFrom(DEFAULT_MESSAGE_VALID_FROM)
            .messageValidUntil(DEFAULT_MESSAGE_VALID_UNTIL)
            .weight(DEFAULT_WEIGHT);
        // Add required entity
        AppUser messageRecipient = AppUserResourceIntTest.createEntity(em);
        em.persist(messageRecipient);
        em.flush();
        message.getMessageRecipients().add(messageRecipient);
        return message;
    }

    @Before
    public void initTest() {
        message = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getMessageShortDescription()).isEqualTo(DEFAULT_MESSAGE_SHORT_DESCRIPTION);
        assertThat(testMessage.getMessageInitTime()).isEqualTo(DEFAULT_MESSAGE_INIT_TIME);
        assertThat(testMessage.getMessageText()).isEqualTo(DEFAULT_MESSAGE_TEXT);
        assertThat(testMessage.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testMessage.getMessageValidFrom()).isEqualTo(DEFAULT_MESSAGE_VALID_FROM);
        assertThat(testMessage.getMessageValidUntil()).isEqualTo(DEFAULT_MESSAGE_VALID_UNTIL);
        assertThat(testMessage.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId(1L);
        MessageDTO messageDTO = messageMapper.toDto(message);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMessageShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setMessageShortDescription(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].messageShortDescription").value(hasItem(DEFAULT_MESSAGE_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].messageInitTime").value(hasItem(DEFAULT_MESSAGE_INIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].messageText").value(hasItem(DEFAULT_MESSAGE_TEXT.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].messageValidFrom").value(hasItem(DEFAULT_MESSAGE_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].messageValidUntil").value(hasItem(DEFAULT_MESSAGE_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.messageShortDescription").value(DEFAULT_MESSAGE_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.messageInitTime").value(DEFAULT_MESSAGE_INIT_TIME.toString()))
            .andExpect(jsonPath("$.messageText").value(DEFAULT_MESSAGE_TEXT.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.messageValidFrom").value(DEFAULT_MESSAGE_VALID_FROM.toString()))
            .andExpect(jsonPath("$.messageValidUntil").value(DEFAULT_MESSAGE_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findOne(message.getId());
        // Disconnect from session so that the updates on updatedMessage are not directly saved in db
        em.detach(updatedMessage);
        updatedMessage
            .messageShortDescription(UPDATED_MESSAGE_SHORT_DESCRIPTION)
            .messageInitTime(UPDATED_MESSAGE_INIT_TIME)
            .messageText(UPDATED_MESSAGE_TEXT)
            .language(UPDATED_LANGUAGE)
            .messageValidFrom(UPDATED_MESSAGE_VALID_FROM)
            .messageValidUntil(UPDATED_MESSAGE_VALID_UNTIL)
            .weight(UPDATED_WEIGHT);
        MessageDTO messageDTO = messageMapper.toDto(updatedMessage);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getMessageShortDescription()).isEqualTo(UPDATED_MESSAGE_SHORT_DESCRIPTION);
        assertThat(testMessage.getMessageInitTime()).isEqualTo(UPDATED_MESSAGE_INIT_TIME);
        assertThat(testMessage.getMessageText()).isEqualTo(UPDATED_MESSAGE_TEXT);
        assertThat(testMessage.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testMessage.getMessageValidFrom()).isEqualTo(UPDATED_MESSAGE_VALID_FROM);
        assertThat(testMessage.getMessageValidUntil()).isEqualTo(UPDATED_MESSAGE_VALID_UNTIL);
        assertThat(testMessage.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Get the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = new Message();
        message1.setId(1L);
        Message message2 = new Message();
        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);
        message2.setId(2L);
        assertThat(message1).isNotEqualTo(message2);
        message1.setId(null);
        assertThat(message1).isNotEqualTo(message2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageDTO.class);
        MessageDTO messageDTO1 = new MessageDTO();
        messageDTO1.setId(1L);
        MessageDTO messageDTO2 = new MessageDTO();
        assertThat(messageDTO1).isNotEqualTo(messageDTO2);
        messageDTO2.setId(messageDTO1.getId());
        assertThat(messageDTO1).isEqualTo(messageDTO2);
        messageDTO2.setId(2L);
        assertThat(messageDTO1).isNotEqualTo(messageDTO2);
        messageDTO1.setId(null);
        assertThat(messageDTO1).isNotEqualTo(messageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(messageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(messageMapper.fromId(null)).isNull();
    }
}
