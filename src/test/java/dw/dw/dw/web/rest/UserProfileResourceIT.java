package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.UserProfile;
import dw.dw.dw.repository.UserProfileRepository;
import dw.dw.dw.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static dw.dw.dw.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class UserProfileResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEMOGRAF = 1;
    private static final Integer UPDATED_DEMOGRAF = 2;

    private static final Integer DEFAULT_SOCIAL = 1;
    private static final Integer UPDATED_SOCIAL = 2;

    private static final Integer DEFAULT_PROC_CLIN = 1;
    private static final Integer UPDATED_PROC_CLIN = 2;

    private static final Integer DEFAULT_DIAL_ENF = 1;
    private static final Integer UPDATED_DIAL_ENF = 2;

    private static final Integer DEFAULT_DIAL_STAT = 1;
    private static final Integer UPDATED_DIAL_STAT = 2;

    private static final Integer DEFAULT_QUALI_STAT = 1;
    private static final Integer UPDATED_QUALI_STAT = 2;

    private static final Integer DEFAULT_LAB_LINK = 1;
    private static final Integer UPDATED_LAB_LINK = 2;

    private static final Integer DEFAULT_GID_LINK = 1;
    private static final Integer UPDATED_GID_LINK = 2;

    private static final Integer DEFAULT_ASTERIX_FARMA = 1;
    private static final Integer UPDATED_ASTERIX_FARMA = 2;

    private static final Integer DEFAULT_ASTERIX_GAB_MED = 1;
    private static final Integer UPDATED_ASTERIX_GAB_MED = 2;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserProfileResource userProfileResource = new UserProfileResource(userProfileRepository);
        this.restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .nome(DEFAULT_NOME)
            .demograf(DEFAULT_DEMOGRAF)
            .social(DEFAULT_SOCIAL)
            .procClin(DEFAULT_PROC_CLIN)
            .dialEnf(DEFAULT_DIAL_ENF)
            .dialStat(DEFAULT_DIAL_STAT)
            .qualiStat(DEFAULT_QUALI_STAT)
            .labLink(DEFAULT_LAB_LINK)
            .gidLink(DEFAULT_GID_LINK)
            .asterixFarma(DEFAULT_ASTERIX_FARMA)
            .asterixGabMed(DEFAULT_ASTERIX_GAB_MED);
        return userProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .nome(UPDATED_NOME)
            .demograf(UPDATED_DEMOGRAF)
            .social(UPDATED_SOCIAL)
            .procClin(UPDATED_PROC_CLIN)
            .dialEnf(UPDATED_DIAL_ENF)
            .dialStat(UPDATED_DIAL_STAT)
            .qualiStat(UPDATED_QUALI_STAT)
            .labLink(UPDATED_LAB_LINK)
            .gidLink(UPDATED_GID_LINK)
            .asterixFarma(UPDATED_ASTERIX_FARMA)
            .asterixGabMed(UPDATED_ASTERIX_GAB_MED);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUserProfile.getDemograf()).isEqualTo(DEFAULT_DEMOGRAF);
        assertThat(testUserProfile.getSocial()).isEqualTo(DEFAULT_SOCIAL);
        assertThat(testUserProfile.getProcClin()).isEqualTo(DEFAULT_PROC_CLIN);
        assertThat(testUserProfile.getDialEnf()).isEqualTo(DEFAULT_DIAL_ENF);
        assertThat(testUserProfile.getDialStat()).isEqualTo(DEFAULT_DIAL_STAT);
        assertThat(testUserProfile.getQualiStat()).isEqualTo(DEFAULT_QUALI_STAT);
        assertThat(testUserProfile.getLabLink()).isEqualTo(DEFAULT_LAB_LINK);
        assertThat(testUserProfile.getGidLink()).isEqualTo(DEFAULT_GID_LINK);
        assertThat(testUserProfile.getAsterixFarma()).isEqualTo(DEFAULT_ASTERIX_FARMA);
        assertThat(testUserProfile.getAsterixGabMed()).isEqualTo(DEFAULT_ASTERIX_GAB_MED);
    }

    @Test
    @Transactional
    public void createUserProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile with an existing ID
        userProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].demograf").value(hasItem(DEFAULT_DEMOGRAF)))
            .andExpect(jsonPath("$.[*].social").value(hasItem(DEFAULT_SOCIAL)))
            .andExpect(jsonPath("$.[*].procClin").value(hasItem(DEFAULT_PROC_CLIN)))
            .andExpect(jsonPath("$.[*].dialEnf").value(hasItem(DEFAULT_DIAL_ENF)))
            .andExpect(jsonPath("$.[*].dialStat").value(hasItem(DEFAULT_DIAL_STAT)))
            .andExpect(jsonPath("$.[*].qualiStat").value(hasItem(DEFAULT_QUALI_STAT)))
            .andExpect(jsonPath("$.[*].labLink").value(hasItem(DEFAULT_LAB_LINK)))
            .andExpect(jsonPath("$.[*].gidLink").value(hasItem(DEFAULT_GID_LINK)))
            .andExpect(jsonPath("$.[*].asterixFarma").value(hasItem(DEFAULT_ASTERIX_FARMA)))
            .andExpect(jsonPath("$.[*].asterixGabMed").value(hasItem(DEFAULT_ASTERIX_GAB_MED)));
    }
    
    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.demograf").value(DEFAULT_DEMOGRAF))
            .andExpect(jsonPath("$.social").value(DEFAULT_SOCIAL))
            .andExpect(jsonPath("$.procClin").value(DEFAULT_PROC_CLIN))
            .andExpect(jsonPath("$.dialEnf").value(DEFAULT_DIAL_ENF))
            .andExpect(jsonPath("$.dialStat").value(DEFAULT_DIAL_STAT))
            .andExpect(jsonPath("$.qualiStat").value(DEFAULT_QUALI_STAT))
            .andExpect(jsonPath("$.labLink").value(DEFAULT_LAB_LINK))
            .andExpect(jsonPath("$.gidLink").value(DEFAULT_GID_LINK))
            .andExpect(jsonPath("$.asterixFarma").value(DEFAULT_ASTERIX_FARMA))
            .andExpect(jsonPath("$.asterixGabMed").value(DEFAULT_ASTERIX_GAB_MED));
    }

    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .nome(UPDATED_NOME)
            .demograf(UPDATED_DEMOGRAF)
            .social(UPDATED_SOCIAL)
            .procClin(UPDATED_PROC_CLIN)
            .dialEnf(UPDATED_DIAL_ENF)
            .dialStat(UPDATED_DIAL_STAT)
            .qualiStat(UPDATED_QUALI_STAT)
            .labLink(UPDATED_LAB_LINK)
            .gidLink(UPDATED_GID_LINK)
            .asterixFarma(UPDATED_ASTERIX_FARMA)
            .asterixGabMed(UPDATED_ASTERIX_GAB_MED);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserProfile)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUserProfile.getDemograf()).isEqualTo(UPDATED_DEMOGRAF);
        assertThat(testUserProfile.getSocial()).isEqualTo(UPDATED_SOCIAL);
        assertThat(testUserProfile.getProcClin()).isEqualTo(UPDATED_PROC_CLIN);
        assertThat(testUserProfile.getDialEnf()).isEqualTo(UPDATED_DIAL_ENF);
        assertThat(testUserProfile.getDialStat()).isEqualTo(UPDATED_DIAL_STAT);
        assertThat(testUserProfile.getQualiStat()).isEqualTo(UPDATED_QUALI_STAT);
        assertThat(testUserProfile.getLabLink()).isEqualTo(UPDATED_LAB_LINK);
        assertThat(testUserProfile.getGidLink()).isEqualTo(UPDATED_GID_LINK);
        assertThat(testUserProfile.getAsterixFarma()).isEqualTo(UPDATED_ASTERIX_FARMA);
        assertThat(testUserProfile.getAsterixGabMed()).isEqualTo(UPDATED_ASTERIX_GAB_MED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Create the UserProfile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
