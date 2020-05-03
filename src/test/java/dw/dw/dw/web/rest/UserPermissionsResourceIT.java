package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.UserPermissions;
import dw.dw.dw.repository.UserPermissionsRepository;
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
 * Integration tests for the {@link UserPermissionsResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class UserPermissionsResourceIT {

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
    private UserPermissionsRepository userPermissionsRepository;

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

    private MockMvc restUserPermissionsMockMvc;

    private UserPermissions userPermissions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPermissionsResource userPermissionsResource = new UserPermissionsResource(userPermissionsRepository);
        this.restUserPermissionsMockMvc = MockMvcBuilders.standaloneSetup(userPermissionsResource)
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
    public static UserPermissions createEntity(EntityManager em) {
        UserPermissions userPermissions = new UserPermissions()
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
        return userPermissions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPermissions createUpdatedEntity(EntityManager em) {
        UserPermissions userPermissions = new UserPermissions()
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
        return userPermissions;
    }

    @BeforeEach
    public void initTest() {
        userPermissions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPermissions() throws Exception {
        int databaseSizeBeforeCreate = userPermissionsRepository.findAll().size();

        // Create the UserPermissions
        restUserPermissionsMockMvc.perform(post("/api/user-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPermissions)))
            .andExpect(status().isCreated());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeCreate + 1);
        UserPermissions testUserPermissions = userPermissionsList.get(userPermissionsList.size() - 1);
        assertThat(testUserPermissions.getDemograf()).isEqualTo(DEFAULT_DEMOGRAF);
        assertThat(testUserPermissions.getSocial()).isEqualTo(DEFAULT_SOCIAL);
        assertThat(testUserPermissions.getProcClin()).isEqualTo(DEFAULT_PROC_CLIN);
        assertThat(testUserPermissions.getDialEnf()).isEqualTo(DEFAULT_DIAL_ENF);
        assertThat(testUserPermissions.getDialStat()).isEqualTo(DEFAULT_DIAL_STAT);
        assertThat(testUserPermissions.getQualiStat()).isEqualTo(DEFAULT_QUALI_STAT);
        assertThat(testUserPermissions.getLabLink()).isEqualTo(DEFAULT_LAB_LINK);
        assertThat(testUserPermissions.getGidLink()).isEqualTo(DEFAULT_GID_LINK);
        assertThat(testUserPermissions.getAsterixFarma()).isEqualTo(DEFAULT_ASTERIX_FARMA);
        assertThat(testUserPermissions.getAsterixGabMed()).isEqualTo(DEFAULT_ASTERIX_GAB_MED);
    }

    @Test
    @Transactional
    public void createUserPermissionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPermissionsRepository.findAll().size();

        // Create the UserPermissions with an existing ID
        userPermissions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPermissionsMockMvc.perform(post("/api/user-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPermissions)))
            .andExpect(status().isBadRequest());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.saveAndFlush(userPermissions);

        // Get all the userPermissionsList
        restUserPermissionsMockMvc.perform(get("/api/user-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPermissions.getId().intValue())))
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
    public void getUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.saveAndFlush(userPermissions);

        // Get the userPermissions
        restUserPermissionsMockMvc.perform(get("/api/user-permissions/{id}", userPermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPermissions.getId().intValue()))
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
    public void getNonExistingUserPermissions() throws Exception {
        // Get the userPermissions
        restUserPermissionsMockMvc.perform(get("/api/user-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.saveAndFlush(userPermissions);

        int databaseSizeBeforeUpdate = userPermissionsRepository.findAll().size();

        // Update the userPermissions
        UserPermissions updatedUserPermissions = userPermissionsRepository.findById(userPermissions.getId()).get();
        // Disconnect from session so that the updates on updatedUserPermissions are not directly saved in db
        em.detach(updatedUserPermissions);
        updatedUserPermissions
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

        restUserPermissionsMockMvc.perform(put("/api/user-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPermissions)))
            .andExpect(status().isOk());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeUpdate);
        UserPermissions testUserPermissions = userPermissionsList.get(userPermissionsList.size() - 1);
        assertThat(testUserPermissions.getDemograf()).isEqualTo(UPDATED_DEMOGRAF);
        assertThat(testUserPermissions.getSocial()).isEqualTo(UPDATED_SOCIAL);
        assertThat(testUserPermissions.getProcClin()).isEqualTo(UPDATED_PROC_CLIN);
        assertThat(testUserPermissions.getDialEnf()).isEqualTo(UPDATED_DIAL_ENF);
        assertThat(testUserPermissions.getDialStat()).isEqualTo(UPDATED_DIAL_STAT);
        assertThat(testUserPermissions.getQualiStat()).isEqualTo(UPDATED_QUALI_STAT);
        assertThat(testUserPermissions.getLabLink()).isEqualTo(UPDATED_LAB_LINK);
        assertThat(testUserPermissions.getGidLink()).isEqualTo(UPDATED_GID_LINK);
        assertThat(testUserPermissions.getAsterixFarma()).isEqualTo(UPDATED_ASTERIX_FARMA);
        assertThat(testUserPermissions.getAsterixGabMed()).isEqualTo(UPDATED_ASTERIX_GAB_MED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPermissions() throws Exception {
        int databaseSizeBeforeUpdate = userPermissionsRepository.findAll().size();

        // Create the UserPermissions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPermissionsMockMvc.perform(put("/api/user-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPermissions)))
            .andExpect(status().isBadRequest());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.saveAndFlush(userPermissions);

        int databaseSizeBeforeDelete = userPermissionsRepository.findAll().size();

        // Delete the userPermissions
        restUserPermissionsMockMvc.perform(delete("/api/user-permissions/{id}", userPermissions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
