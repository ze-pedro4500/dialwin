package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.Turnos;
import dw.dw.dw.repository.TurnosRepository;
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
 * Integration tests for the {@link TurnosResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class TurnosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TurnosRepository turnosRepository;

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

    private MockMvc restTurnosMockMvc;

    private Turnos turnos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TurnosResource turnosResource = new TurnosResource(turnosRepository);
        this.restTurnosMockMvc = MockMvcBuilders.standaloneSetup(turnosResource)
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
    public static Turnos createEntity(EntityManager em) {
        Turnos turnos = new Turnos()
            .nome(DEFAULT_NOME);
        return turnos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turnos createUpdatedEntity(EntityManager em) {
        Turnos turnos = new Turnos()
            .nome(UPDATED_NOME);
        return turnos;
    }

    @BeforeEach
    public void initTest() {
        turnos = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurnos() throws Exception {
        int databaseSizeBeforeCreate = turnosRepository.findAll().size();

        // Create the Turnos
        restTurnosMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnos)))
            .andExpect(status().isCreated());

        // Validate the Turnos in the database
        List<Turnos> turnosList = turnosRepository.findAll();
        assertThat(turnosList).hasSize(databaseSizeBeforeCreate + 1);
        Turnos testTurnos = turnosList.get(turnosList.size() - 1);
        assertThat(testTurnos.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTurnosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turnosRepository.findAll().size();

        // Create the Turnos with an existing ID
        turnos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnosMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnos)))
            .andExpect(status().isBadRequest());

        // Validate the Turnos in the database
        List<Turnos> turnosList = turnosRepository.findAll();
        assertThat(turnosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTurnos() throws Exception {
        // Initialize the database
        turnosRepository.saveAndFlush(turnos);

        // Get all the turnosList
        restTurnosMockMvc.perform(get("/api/turnos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turnos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTurnos() throws Exception {
        // Initialize the database
        turnosRepository.saveAndFlush(turnos);

        // Get the turnos
        restTurnosMockMvc.perform(get("/api/turnos/{id}", turnos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turnos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingTurnos() throws Exception {
        // Get the turnos
        restTurnosMockMvc.perform(get("/api/turnos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurnos() throws Exception {
        // Initialize the database
        turnosRepository.saveAndFlush(turnos);

        int databaseSizeBeforeUpdate = turnosRepository.findAll().size();

        // Update the turnos
        Turnos updatedTurnos = turnosRepository.findById(turnos.getId()).get();
        // Disconnect from session so that the updates on updatedTurnos are not directly saved in db
        em.detach(updatedTurnos);
        updatedTurnos
            .nome(UPDATED_NOME);

        restTurnosMockMvc.perform(put("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTurnos)))
            .andExpect(status().isOk());

        // Validate the Turnos in the database
        List<Turnos> turnosList = turnosRepository.findAll();
        assertThat(turnosList).hasSize(databaseSizeBeforeUpdate);
        Turnos testTurnos = turnosList.get(turnosList.size() - 1);
        assertThat(testTurnos.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTurnos() throws Exception {
        int databaseSizeBeforeUpdate = turnosRepository.findAll().size();

        // Create the Turnos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnosMockMvc.perform(put("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnos)))
            .andExpect(status().isBadRequest());

        // Validate the Turnos in the database
        List<Turnos> turnosList = turnosRepository.findAll();
        assertThat(turnosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTurnos() throws Exception {
        // Initialize the database
        turnosRepository.saveAndFlush(turnos);

        int databaseSizeBeforeDelete = turnosRepository.findAll().size();

        // Delete the turnos
        restTurnosMockMvc.perform(delete("/api/turnos/{id}", turnos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turnos> turnosList = turnosRepository.findAll();
        assertThat(turnosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
