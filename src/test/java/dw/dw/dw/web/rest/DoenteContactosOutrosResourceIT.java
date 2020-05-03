package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.DoenteContactosOutros;
import dw.dw.dw.repository.DoenteContactosOutrosRepository;
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
 * Integration tests for the {@link DoenteContactosOutrosResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class DoenteContactosOutrosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_PARENTESCO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COABITA = false;
    private static final Boolean UPDATED_COABITA = true;

    private static final Integer DEFAULT_TELEF = 1;
    private static final Integer UPDATED_TELEF = 2;

    private static final String DEFAULT_OCUPACAO = "AAAAAAAAAA";
    private static final String UPDATED_OCUPACAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREFERENCIAL = false;
    private static final Boolean UPDATED_PREFERENCIAL = true;

    @Autowired
    private DoenteContactosOutrosRepository doenteContactosOutrosRepository;

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

    private MockMvc restDoenteContactosOutrosMockMvc;

    private DoenteContactosOutros doenteContactosOutros;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoenteContactosOutrosResource doenteContactosOutrosResource = new DoenteContactosOutrosResource(doenteContactosOutrosRepository);
        this.restDoenteContactosOutrosMockMvc = MockMvcBuilders.standaloneSetup(doenteContactosOutrosResource)
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
    public static DoenteContactosOutros createEntity(EntityManager em) {
        DoenteContactosOutros doenteContactosOutros = new DoenteContactosOutros()
            .nome(DEFAULT_NOME)
            .parentesco(DEFAULT_PARENTESCO)
            .coabita(DEFAULT_COABITA)
            .telef(DEFAULT_TELEF)
            .ocupacao(DEFAULT_OCUPACAO)
            .obs(DEFAULT_OBS)
            .preferencial(DEFAULT_PREFERENCIAL);
        return doenteContactosOutros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoenteContactosOutros createUpdatedEntity(EntityManager em) {
        DoenteContactosOutros doenteContactosOutros = new DoenteContactosOutros()
            .nome(UPDATED_NOME)
            .parentesco(UPDATED_PARENTESCO)
            .coabita(UPDATED_COABITA)
            .telef(UPDATED_TELEF)
            .ocupacao(UPDATED_OCUPACAO)
            .obs(UPDATED_OBS)
            .preferencial(UPDATED_PREFERENCIAL);
        return doenteContactosOutros;
    }

    @BeforeEach
    public void initTest() {
        doenteContactosOutros = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenteContactosOutros() throws Exception {
        int databaseSizeBeforeCreate = doenteContactosOutrosRepository.findAll().size();

        // Create the DoenteContactosOutros
        restDoenteContactosOutrosMockMvc.perform(post("/api/doente-contactos-outros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteContactosOutros)))
            .andExpect(status().isCreated());

        // Validate the DoenteContactosOutros in the database
        List<DoenteContactosOutros> doenteContactosOutrosList = doenteContactosOutrosRepository.findAll();
        assertThat(doenteContactosOutrosList).hasSize(databaseSizeBeforeCreate + 1);
        DoenteContactosOutros testDoenteContactosOutros = doenteContactosOutrosList.get(doenteContactosOutrosList.size() - 1);
        assertThat(testDoenteContactosOutros.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDoenteContactosOutros.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testDoenteContactosOutros.isCoabita()).isEqualTo(DEFAULT_COABITA);
        assertThat(testDoenteContactosOutros.getTelef()).isEqualTo(DEFAULT_TELEF);
        assertThat(testDoenteContactosOutros.getOcupacao()).isEqualTo(DEFAULT_OCUPACAO);
        assertThat(testDoenteContactosOutros.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testDoenteContactosOutros.isPreferencial()).isEqualTo(DEFAULT_PREFERENCIAL);
    }

    @Test
    @Transactional
    public void createDoenteContactosOutrosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doenteContactosOutrosRepository.findAll().size();

        // Create the DoenteContactosOutros with an existing ID
        doenteContactosOutros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoenteContactosOutrosMockMvc.perform(post("/api/doente-contactos-outros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteContactosOutros)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteContactosOutros in the database
        List<DoenteContactosOutros> doenteContactosOutrosList = doenteContactosOutrosRepository.findAll();
        assertThat(doenteContactosOutrosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoenteContactosOutros() throws Exception {
        // Initialize the database
        doenteContactosOutrosRepository.saveAndFlush(doenteContactosOutros);

        // Get all the doenteContactosOutrosList
        restDoenteContactosOutrosMockMvc.perform(get("/api/doente-contactos-outros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenteContactosOutros.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO)))
            .andExpect(jsonPath("$.[*].coabita").value(hasItem(DEFAULT_COABITA.booleanValue())))
            .andExpect(jsonPath("$.[*].telef").value(hasItem(DEFAULT_TELEF)))
            .andExpect(jsonPath("$.[*].ocupacao").value(hasItem(DEFAULT_OCUPACAO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].preferencial").value(hasItem(DEFAULT_PREFERENCIAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDoenteContactosOutros() throws Exception {
        // Initialize the database
        doenteContactosOutrosRepository.saveAndFlush(doenteContactosOutros);

        // Get the doenteContactosOutros
        restDoenteContactosOutrosMockMvc.perform(get("/api/doente-contactos-outros/{id}", doenteContactosOutros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenteContactosOutros.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.parentesco").value(DEFAULT_PARENTESCO))
            .andExpect(jsonPath("$.coabita").value(DEFAULT_COABITA.booleanValue()))
            .andExpect(jsonPath("$.telef").value(DEFAULT_TELEF))
            .andExpect(jsonPath("$.ocupacao").value(DEFAULT_OCUPACAO))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS))
            .andExpect(jsonPath("$.preferencial").value(DEFAULT_PREFERENCIAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDoenteContactosOutros() throws Exception {
        // Get the doenteContactosOutros
        restDoenteContactosOutrosMockMvc.perform(get("/api/doente-contactos-outros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenteContactosOutros() throws Exception {
        // Initialize the database
        doenteContactosOutrosRepository.saveAndFlush(doenteContactosOutros);

        int databaseSizeBeforeUpdate = doenteContactosOutrosRepository.findAll().size();

        // Update the doenteContactosOutros
        DoenteContactosOutros updatedDoenteContactosOutros = doenteContactosOutrosRepository.findById(doenteContactosOutros.getId()).get();
        // Disconnect from session so that the updates on updatedDoenteContactosOutros are not directly saved in db
        em.detach(updatedDoenteContactosOutros);
        updatedDoenteContactosOutros
            .nome(UPDATED_NOME)
            .parentesco(UPDATED_PARENTESCO)
            .coabita(UPDATED_COABITA)
            .telef(UPDATED_TELEF)
            .ocupacao(UPDATED_OCUPACAO)
            .obs(UPDATED_OBS)
            .preferencial(UPDATED_PREFERENCIAL);

        restDoenteContactosOutrosMockMvc.perform(put("/api/doente-contactos-outros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenteContactosOutros)))
            .andExpect(status().isOk());

        // Validate the DoenteContactosOutros in the database
        List<DoenteContactosOutros> doenteContactosOutrosList = doenteContactosOutrosRepository.findAll();
        assertThat(doenteContactosOutrosList).hasSize(databaseSizeBeforeUpdate);
        DoenteContactosOutros testDoenteContactosOutros = doenteContactosOutrosList.get(doenteContactosOutrosList.size() - 1);
        assertThat(testDoenteContactosOutros.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDoenteContactosOutros.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testDoenteContactosOutros.isCoabita()).isEqualTo(UPDATED_COABITA);
        assertThat(testDoenteContactosOutros.getTelef()).isEqualTo(UPDATED_TELEF);
        assertThat(testDoenteContactosOutros.getOcupacao()).isEqualTo(UPDATED_OCUPACAO);
        assertThat(testDoenteContactosOutros.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testDoenteContactosOutros.isPreferencial()).isEqualTo(UPDATED_PREFERENCIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenteContactosOutros() throws Exception {
        int databaseSizeBeforeUpdate = doenteContactosOutrosRepository.findAll().size();

        // Create the DoenteContactosOutros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoenteContactosOutrosMockMvc.perform(put("/api/doente-contactos-outros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteContactosOutros)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteContactosOutros in the database
        List<DoenteContactosOutros> doenteContactosOutrosList = doenteContactosOutrosRepository.findAll();
        assertThat(doenteContactosOutrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenteContactosOutros() throws Exception {
        // Initialize the database
        doenteContactosOutrosRepository.saveAndFlush(doenteContactosOutros);

        int databaseSizeBeforeDelete = doenteContactosOutrosRepository.findAll().size();

        // Delete the doenteContactosOutros
        restDoenteContactosOutrosMockMvc.perform(delete("/api/doente-contactos-outros/{id}", doenteContactosOutros.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoenteContactosOutros> doenteContactosOutrosList = doenteContactosOutrosRepository.findAll();
        assertThat(doenteContactosOutrosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
