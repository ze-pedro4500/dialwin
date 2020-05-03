package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.UserExtra;
import dw.dw.dw.repository.UserExtraRepository;
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
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class UserExtraResourceIT {

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_MORADA = "AAAAAAAAAA";
    private static final String UPDATED_MORADA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_P = "AAAAAAAAAA";
    private static final String UPDATED_COD_P = "BBBBBBBBBB";

    private static final String DEFAULT_TELEF = "AAAAAAAAAA";
    private static final String UPDATED_TELEF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PERMISS_CHANGE = false;
    private static final Boolean UPDATED_PERMISS_CHANGE = true;

    private static final Integer DEFAULT_NIF = 1;
    private static final Integer UPDATED_NIF = 2;

    @Autowired
    private UserExtraRepository userExtraRepository;

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

    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserExtraResource userExtraResource = new UserExtraResource(userExtraRepository);
        this.restUserExtraMockMvc = MockMvcBuilders.standaloneSetup(userExtraResource)
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
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .activo(DEFAULT_ACTIVO)
            .nome(DEFAULT_NOME)
            .morada(DEFAULT_MORADA)
            .codP(DEFAULT_COD_P)
            .telef(DEFAULT_TELEF)
            .permissChange(DEFAULT_PERMISS_CHANGE)
            .nif(DEFAULT_NIF);
        return userExtra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .activo(UPDATED_ACTIVO)
            .nome(UPDATED_NOME)
            .morada(UPDATED_MORADA)
            .codP(UPDATED_COD_P)
            .telef(UPDATED_TELEF)
            .permissChange(UPDATED_PERMISS_CHANGE)
            .nif(UPDATED_NIF);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testUserExtra.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUserExtra.getMorada()).isEqualTo(DEFAULT_MORADA);
        assertThat(testUserExtra.getCodP()).isEqualTo(DEFAULT_COD_P);
        assertThat(testUserExtra.getTelef()).isEqualTo(DEFAULT_TELEF);
        assertThat(testUserExtra.isPermissChange()).isEqualTo(DEFAULT_PERMISS_CHANGE);
        assertThat(testUserExtra.getNif()).isEqualTo(DEFAULT_NIF);
    }

    @Test
    @Transactional
    public void createUserExtraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // Create the UserExtra with an existing ID
        userExtra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc.perform(post("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc.perform(get("/api/user-extras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].morada").value(hasItem(DEFAULT_MORADA)))
            .andExpect(jsonPath("$.[*].codP").value(hasItem(DEFAULT_COD_P)))
            .andExpect(jsonPath("$.[*].telef").value(hasItem(DEFAULT_TELEF)))
            .andExpect(jsonPath("$.[*].permissChange").value(hasItem(DEFAULT_PERMISS_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)));
    }
    
    @Test
    @Transactional
    public void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.morada").value(DEFAULT_MORADA))
            .andExpect(jsonPath("$.codP").value(DEFAULT_COD_P))
            .andExpect(jsonPath("$.telef").value(DEFAULT_TELEF))
            .andExpect(jsonPath("$.permissChange").value(DEFAULT_PERMISS_CHANGE.booleanValue()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF));
    }

    @Test
    @Transactional
    public void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get("/api/user-extras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra
            .activo(UPDATED_ACTIVO)
            .nome(UPDATED_NOME)
            .morada(UPDATED_MORADA)
            .codP(UPDATED_COD_P)
            .telef(UPDATED_TELEF)
            .permissChange(UPDATED_PERMISS_CHANGE)
            .nif(UPDATED_NIF);

        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExtra)))
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testUserExtra.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUserExtra.getMorada()).isEqualTo(UPDATED_MORADA);
        assertThat(testUserExtra.getCodP()).isEqualTo(UPDATED_COD_P);
        assertThat(testUserExtra.getTelef()).isEqualTo(UPDATED_TELEF);
        assertThat(testUserExtra.isPermissChange()).isEqualTo(UPDATED_PERMISS_CHANGE);
        assertThat(testUserExtra.getNif()).isEqualTo(UPDATED_NIF);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Create the UserExtra

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc.perform(put("/api/user-extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc.perform(delete("/api/user-extras/{id}", userExtra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
