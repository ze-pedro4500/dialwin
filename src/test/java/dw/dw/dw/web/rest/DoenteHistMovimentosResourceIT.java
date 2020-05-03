package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.DoenteHistMovimentos;
import dw.dw.dw.repository.DoenteHistMovimentosRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static dw.dw.dw.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DoenteHistMovimentosResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class DoenteHistMovimentosResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SITUACAO = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    @Autowired
    private DoenteHistMovimentosRepository doenteHistMovimentosRepository;

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

    private MockMvc restDoenteHistMovimentosMockMvc;

    private DoenteHistMovimentos doenteHistMovimentos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoenteHistMovimentosResource doenteHistMovimentosResource = new DoenteHistMovimentosResource(doenteHistMovimentosRepository);
        this.restDoenteHistMovimentosMockMvc = MockMvcBuilders.standaloneSetup(doenteHistMovimentosResource)
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
    public static DoenteHistMovimentos createEntity(EntityManager em) {
        DoenteHistMovimentos doenteHistMovimentos = new DoenteHistMovimentos()
            .data(DEFAULT_DATA)
            .situacao(DEFAULT_SITUACAO)
            .obs(DEFAULT_OBS);
        return doenteHistMovimentos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoenteHistMovimentos createUpdatedEntity(EntityManager em) {
        DoenteHistMovimentos doenteHistMovimentos = new DoenteHistMovimentos()
            .data(UPDATED_DATA)
            .situacao(UPDATED_SITUACAO)
            .obs(UPDATED_OBS);
        return doenteHistMovimentos;
    }

    @BeforeEach
    public void initTest() {
        doenteHistMovimentos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenteHistMovimentos() throws Exception {
        int databaseSizeBeforeCreate = doenteHistMovimentosRepository.findAll().size();

        // Create the DoenteHistMovimentos
        restDoenteHistMovimentosMockMvc.perform(post("/api/doente-hist-movimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteHistMovimentos)))
            .andExpect(status().isCreated());

        // Validate the DoenteHistMovimentos in the database
        List<DoenteHistMovimentos> doenteHistMovimentosList = doenteHistMovimentosRepository.findAll();
        assertThat(doenteHistMovimentosList).hasSize(databaseSizeBeforeCreate + 1);
        DoenteHistMovimentos testDoenteHistMovimentos = doenteHistMovimentosList.get(doenteHistMovimentosList.size() - 1);
        assertThat(testDoenteHistMovimentos.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDoenteHistMovimentos.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testDoenteHistMovimentos.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    public void createDoenteHistMovimentosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doenteHistMovimentosRepository.findAll().size();

        // Create the DoenteHistMovimentos with an existing ID
        doenteHistMovimentos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoenteHistMovimentosMockMvc.perform(post("/api/doente-hist-movimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteHistMovimentos)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteHistMovimentos in the database
        List<DoenteHistMovimentos> doenteHistMovimentosList = doenteHistMovimentosRepository.findAll();
        assertThat(doenteHistMovimentosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoenteHistMovimentos() throws Exception {
        // Initialize the database
        doenteHistMovimentosRepository.saveAndFlush(doenteHistMovimentos);

        // Get all the doenteHistMovimentosList
        restDoenteHistMovimentosMockMvc.perform(get("/api/doente-hist-movimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenteHistMovimentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }
    
    @Test
    @Transactional
    public void getDoenteHistMovimentos() throws Exception {
        // Initialize the database
        doenteHistMovimentosRepository.saveAndFlush(doenteHistMovimentos);

        // Get the doenteHistMovimentos
        restDoenteHistMovimentosMockMvc.perform(get("/api/doente-hist-movimentos/{id}", doenteHistMovimentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenteHistMovimentos.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    public void getNonExistingDoenteHistMovimentos() throws Exception {
        // Get the doenteHistMovimentos
        restDoenteHistMovimentosMockMvc.perform(get("/api/doente-hist-movimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenteHistMovimentos() throws Exception {
        // Initialize the database
        doenteHistMovimentosRepository.saveAndFlush(doenteHistMovimentos);

        int databaseSizeBeforeUpdate = doenteHistMovimentosRepository.findAll().size();

        // Update the doenteHistMovimentos
        DoenteHistMovimentos updatedDoenteHistMovimentos = doenteHistMovimentosRepository.findById(doenteHistMovimentos.getId()).get();
        // Disconnect from session so that the updates on updatedDoenteHistMovimentos are not directly saved in db
        em.detach(updatedDoenteHistMovimentos);
        updatedDoenteHistMovimentos
            .data(UPDATED_DATA)
            .situacao(UPDATED_SITUACAO)
            .obs(UPDATED_OBS);

        restDoenteHistMovimentosMockMvc.perform(put("/api/doente-hist-movimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenteHistMovimentos)))
            .andExpect(status().isOk());

        // Validate the DoenteHistMovimentos in the database
        List<DoenteHistMovimentos> doenteHistMovimentosList = doenteHistMovimentosRepository.findAll();
        assertThat(doenteHistMovimentosList).hasSize(databaseSizeBeforeUpdate);
        DoenteHistMovimentos testDoenteHistMovimentos = doenteHistMovimentosList.get(doenteHistMovimentosList.size() - 1);
        assertThat(testDoenteHistMovimentos.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDoenteHistMovimentos.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testDoenteHistMovimentos.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenteHistMovimentos() throws Exception {
        int databaseSizeBeforeUpdate = doenteHistMovimentosRepository.findAll().size();

        // Create the DoenteHistMovimentos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoenteHistMovimentosMockMvc.perform(put("/api/doente-hist-movimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteHistMovimentos)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteHistMovimentos in the database
        List<DoenteHistMovimentos> doenteHistMovimentosList = doenteHistMovimentosRepository.findAll();
        assertThat(doenteHistMovimentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenteHistMovimentos() throws Exception {
        // Initialize the database
        doenteHistMovimentosRepository.saveAndFlush(doenteHistMovimentos);

        int databaseSizeBeforeDelete = doenteHistMovimentosRepository.findAll().size();

        // Delete the doenteHistMovimentos
        restDoenteHistMovimentosMockMvc.perform(delete("/api/doente-hist-movimentos/{id}", doenteHistMovimentos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoenteHistMovimentos> doenteHistMovimentosList = doenteHistMovimentosRepository.findAll();
        assertThat(doenteHistMovimentosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
