package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.HorarioDoente;
import dw.dw.dw.repository.HorarioDoenteRepository;
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
 * Integration tests for the {@link HorarioDoenteResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class HorarioDoenteResourceIT {

    private static final String DEFAULT_DIA = "AAAAAAAAAA";
    private static final String UPDATED_DIA = "BBBBBBBBBB";

    private static final String DEFAULT_TURNO = "AAAAAAAAAA";
    private static final String UPDATED_TURNO = "BBBBBBBBBB";

    private static final Integer DEFAULT_SALA = 1;
    private static final Integer UPDATED_SALA = 2;

    private static final String DEFAULT_POSTO = "AAAAAAAAAA";
    private static final String UPDATED_POSTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACAO = 1;
    private static final Integer UPDATED_DURACAO = 2;

    @Autowired
    private HorarioDoenteRepository horarioDoenteRepository;

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

    private MockMvc restHorarioDoenteMockMvc;

    private HorarioDoente horarioDoente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorarioDoenteResource horarioDoenteResource = new HorarioDoenteResource(horarioDoenteRepository);
        this.restHorarioDoenteMockMvc = MockMvcBuilders.standaloneSetup(horarioDoenteResource)
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
    public static HorarioDoente createEntity(EntityManager em) {
        HorarioDoente horarioDoente = new HorarioDoente()
            .dia(DEFAULT_DIA)
            .turno(DEFAULT_TURNO)
            .sala(DEFAULT_SALA)
            .posto(DEFAULT_POSTO)
            .duracao(DEFAULT_DURACAO);
        return horarioDoente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioDoente createUpdatedEntity(EntityManager em) {
        HorarioDoente horarioDoente = new HorarioDoente()
            .dia(UPDATED_DIA)
            .turno(UPDATED_TURNO)
            .sala(UPDATED_SALA)
            .posto(UPDATED_POSTO)
            .duracao(UPDATED_DURACAO);
        return horarioDoente;
    }

    @BeforeEach
    public void initTest() {
        horarioDoente = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioDoente() throws Exception {
        int databaseSizeBeforeCreate = horarioDoenteRepository.findAll().size();

        // Create the HorarioDoente
        restHorarioDoenteMockMvc.perform(post("/api/horario-doentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDoente)))
            .andExpect(status().isCreated());

        // Validate the HorarioDoente in the database
        List<HorarioDoente> horarioDoenteList = horarioDoenteRepository.findAll();
        assertThat(horarioDoenteList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioDoente testHorarioDoente = horarioDoenteList.get(horarioDoenteList.size() - 1);
        assertThat(testHorarioDoente.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testHorarioDoente.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testHorarioDoente.getSala()).isEqualTo(DEFAULT_SALA);
        assertThat(testHorarioDoente.getPosto()).isEqualTo(DEFAULT_POSTO);
        assertThat(testHorarioDoente.getDuracao()).isEqualTo(DEFAULT_DURACAO);
    }

    @Test
    @Transactional
    public void createHorarioDoenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioDoenteRepository.findAll().size();

        // Create the HorarioDoente with an existing ID
        horarioDoente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioDoenteMockMvc.perform(post("/api/horario-doentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDoente)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioDoente in the database
        List<HorarioDoente> horarioDoenteList = horarioDoenteRepository.findAll();
        assertThat(horarioDoenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHorarioDoentes() throws Exception {
        // Initialize the database
        horarioDoenteRepository.saveAndFlush(horarioDoente);

        // Get all the horarioDoenteList
        restHorarioDoenteMockMvc.perform(get("/api/horario-doentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioDoente.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA)))
            .andExpect(jsonPath("$.[*].posto").value(hasItem(DEFAULT_POSTO)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)));
    }
    
    @Test
    @Transactional
    public void getHorarioDoente() throws Exception {
        // Initialize the database
        horarioDoenteRepository.saveAndFlush(horarioDoente);

        // Get the horarioDoente
        restHorarioDoenteMockMvc.perform(get("/api/horario-doentes/{id}", horarioDoente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horarioDoente.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO))
            .andExpect(jsonPath("$.sala").value(DEFAULT_SALA))
            .andExpect(jsonPath("$.posto").value(DEFAULT_POSTO))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO));
    }

    @Test
    @Transactional
    public void getNonExistingHorarioDoente() throws Exception {
        // Get the horarioDoente
        restHorarioDoenteMockMvc.perform(get("/api/horario-doentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioDoente() throws Exception {
        // Initialize the database
        horarioDoenteRepository.saveAndFlush(horarioDoente);

        int databaseSizeBeforeUpdate = horarioDoenteRepository.findAll().size();

        // Update the horarioDoente
        HorarioDoente updatedHorarioDoente = horarioDoenteRepository.findById(horarioDoente.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioDoente are not directly saved in db
        em.detach(updatedHorarioDoente);
        updatedHorarioDoente
            .dia(UPDATED_DIA)
            .turno(UPDATED_TURNO)
            .sala(UPDATED_SALA)
            .posto(UPDATED_POSTO)
            .duracao(UPDATED_DURACAO);

        restHorarioDoenteMockMvc.perform(put("/api/horario-doentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHorarioDoente)))
            .andExpect(status().isOk());

        // Validate the HorarioDoente in the database
        List<HorarioDoente> horarioDoenteList = horarioDoenteRepository.findAll();
        assertThat(horarioDoenteList).hasSize(databaseSizeBeforeUpdate);
        HorarioDoente testHorarioDoente = horarioDoenteList.get(horarioDoenteList.size() - 1);
        assertThat(testHorarioDoente.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testHorarioDoente.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testHorarioDoente.getSala()).isEqualTo(UPDATED_SALA);
        assertThat(testHorarioDoente.getPosto()).isEqualTo(UPDATED_POSTO);
        assertThat(testHorarioDoente.getDuracao()).isEqualTo(UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioDoente() throws Exception {
        int databaseSizeBeforeUpdate = horarioDoenteRepository.findAll().size();

        // Create the HorarioDoente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioDoenteMockMvc.perform(put("/api/horario-doentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDoente)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioDoente in the database
        List<HorarioDoente> horarioDoenteList = horarioDoenteRepository.findAll();
        assertThat(horarioDoenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHorarioDoente() throws Exception {
        // Initialize the database
        horarioDoenteRepository.saveAndFlush(horarioDoente);

        int databaseSizeBeforeDelete = horarioDoenteRepository.findAll().size();

        // Delete the horarioDoente
        restHorarioDoenteMockMvc.perform(delete("/api/horario-doentes/{id}", horarioDoente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HorarioDoente> horarioDoenteList = horarioDoenteRepository.findAll();
        assertThat(horarioDoenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
