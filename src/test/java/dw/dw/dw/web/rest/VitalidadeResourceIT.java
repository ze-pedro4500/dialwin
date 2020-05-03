package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.Vitalidade;
import dw.dw.dw.repository.VitalidadeRepository;
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
 * Integration tests for the {@link VitalidadeResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class VitalidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERCENTAGEM = 1;
    private static final Integer UPDATED_PERCENTAGEM = 2;

    @Autowired
    private VitalidadeRepository vitalidadeRepository;

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

    private MockMvc restVitalidadeMockMvc;

    private Vitalidade vitalidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VitalidadeResource vitalidadeResource = new VitalidadeResource(vitalidadeRepository);
        this.restVitalidadeMockMvc = MockMvcBuilders.standaloneSetup(vitalidadeResource)
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
    public static Vitalidade createEntity(EntityManager em) {
        Vitalidade vitalidade = new Vitalidade()
            .nome(DEFAULT_NOME)
            .percentagem(DEFAULT_PERCENTAGEM);
        return vitalidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vitalidade createUpdatedEntity(EntityManager em) {
        Vitalidade vitalidade = new Vitalidade()
            .nome(UPDATED_NOME)
            .percentagem(UPDATED_PERCENTAGEM);
        return vitalidade;
    }

    @BeforeEach
    public void initTest() {
        vitalidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createVitalidade() throws Exception {
        int databaseSizeBeforeCreate = vitalidadeRepository.findAll().size();

        // Create the Vitalidade
        restVitalidadeMockMvc.perform(post("/api/vitalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vitalidade)))
            .andExpect(status().isCreated());

        // Validate the Vitalidade in the database
        List<Vitalidade> vitalidadeList = vitalidadeRepository.findAll();
        assertThat(vitalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Vitalidade testVitalidade = vitalidadeList.get(vitalidadeList.size() - 1);
        assertThat(testVitalidade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testVitalidade.getPercentagem()).isEqualTo(DEFAULT_PERCENTAGEM);
    }

    @Test
    @Transactional
    public void createVitalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vitalidadeRepository.findAll().size();

        // Create the Vitalidade with an existing ID
        vitalidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVitalidadeMockMvc.perform(post("/api/vitalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vitalidade)))
            .andExpect(status().isBadRequest());

        // Validate the Vitalidade in the database
        List<Vitalidade> vitalidadeList = vitalidadeRepository.findAll();
        assertThat(vitalidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVitalidades() throws Exception {
        // Initialize the database
        vitalidadeRepository.saveAndFlush(vitalidade);

        // Get all the vitalidadeList
        restVitalidadeMockMvc.perform(get("/api/vitalidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vitalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].percentagem").value(hasItem(DEFAULT_PERCENTAGEM)));
    }
    
    @Test
    @Transactional
    public void getVitalidade() throws Exception {
        // Initialize the database
        vitalidadeRepository.saveAndFlush(vitalidade);

        // Get the vitalidade
        restVitalidadeMockMvc.perform(get("/api/vitalidades/{id}", vitalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vitalidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.percentagem").value(DEFAULT_PERCENTAGEM));
    }

    @Test
    @Transactional
    public void getNonExistingVitalidade() throws Exception {
        // Get the vitalidade
        restVitalidadeMockMvc.perform(get("/api/vitalidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVitalidade() throws Exception {
        // Initialize the database
        vitalidadeRepository.saveAndFlush(vitalidade);

        int databaseSizeBeforeUpdate = vitalidadeRepository.findAll().size();

        // Update the vitalidade
        Vitalidade updatedVitalidade = vitalidadeRepository.findById(vitalidade.getId()).get();
        // Disconnect from session so that the updates on updatedVitalidade are not directly saved in db
        em.detach(updatedVitalidade);
        updatedVitalidade
            .nome(UPDATED_NOME)
            .percentagem(UPDATED_PERCENTAGEM);

        restVitalidadeMockMvc.perform(put("/api/vitalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVitalidade)))
            .andExpect(status().isOk());

        // Validate the Vitalidade in the database
        List<Vitalidade> vitalidadeList = vitalidadeRepository.findAll();
        assertThat(vitalidadeList).hasSize(databaseSizeBeforeUpdate);
        Vitalidade testVitalidade = vitalidadeList.get(vitalidadeList.size() - 1);
        assertThat(testVitalidade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testVitalidade.getPercentagem()).isEqualTo(UPDATED_PERCENTAGEM);
    }

    @Test
    @Transactional
    public void updateNonExistingVitalidade() throws Exception {
        int databaseSizeBeforeUpdate = vitalidadeRepository.findAll().size();

        // Create the Vitalidade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVitalidadeMockMvc.perform(put("/api/vitalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vitalidade)))
            .andExpect(status().isBadRequest());

        // Validate the Vitalidade in the database
        List<Vitalidade> vitalidadeList = vitalidadeRepository.findAll();
        assertThat(vitalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVitalidade() throws Exception {
        // Initialize the database
        vitalidadeRepository.saveAndFlush(vitalidade);

        int databaseSizeBeforeDelete = vitalidadeRepository.findAll().size();

        // Delete the vitalidade
        restVitalidadeMockMvc.perform(delete("/api/vitalidades/{id}", vitalidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vitalidade> vitalidadeList = vitalidadeRepository.findAll();
        assertThat(vitalidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
