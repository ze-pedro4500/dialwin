package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.CentroSaude;
import dw.dw.dw.repository.CentroSaudeRepository;
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
 * Integration tests for the {@link CentroSaudeResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class CentroSaudeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CentroSaudeRepository centroSaudeRepository;

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

    private MockMvc restCentroSaudeMockMvc;

    private CentroSaude centroSaude;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentroSaudeResource centroSaudeResource = new CentroSaudeResource(centroSaudeRepository);
        this.restCentroSaudeMockMvc = MockMvcBuilders.standaloneSetup(centroSaudeResource)
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
    public static CentroSaude createEntity(EntityManager em) {
        CentroSaude centroSaude = new CentroSaude()
            .nome(DEFAULT_NOME);
        return centroSaude;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroSaude createUpdatedEntity(EntityManager em) {
        CentroSaude centroSaude = new CentroSaude()
            .nome(UPDATED_NOME);
        return centroSaude;
    }

    @BeforeEach
    public void initTest() {
        centroSaude = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentroSaude() throws Exception {
        int databaseSizeBeforeCreate = centroSaudeRepository.findAll().size();

        // Create the CentroSaude
        restCentroSaudeMockMvc.perform(post("/api/centro-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centroSaude)))
            .andExpect(status().isCreated());

        // Validate the CentroSaude in the database
        List<CentroSaude> centroSaudeList = centroSaudeRepository.findAll();
        assertThat(centroSaudeList).hasSize(databaseSizeBeforeCreate + 1);
        CentroSaude testCentroSaude = centroSaudeList.get(centroSaudeList.size() - 1);
        assertThat(testCentroSaude.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createCentroSaudeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centroSaudeRepository.findAll().size();

        // Create the CentroSaude with an existing ID
        centroSaude.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroSaudeMockMvc.perform(post("/api/centro-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centroSaude)))
            .andExpect(status().isBadRequest());

        // Validate the CentroSaude in the database
        List<CentroSaude> centroSaudeList = centroSaudeRepository.findAll();
        assertThat(centroSaudeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCentroSaudes() throws Exception {
        // Initialize the database
        centroSaudeRepository.saveAndFlush(centroSaude);

        // Get all the centroSaudeList
        restCentroSaudeMockMvc.perform(get("/api/centro-saudes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getCentroSaude() throws Exception {
        // Initialize the database
        centroSaudeRepository.saveAndFlush(centroSaude);

        // Get the centroSaude
        restCentroSaudeMockMvc.perform(get("/api/centro-saudes/{id}", centroSaude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centroSaude.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingCentroSaude() throws Exception {
        // Get the centroSaude
        restCentroSaudeMockMvc.perform(get("/api/centro-saudes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentroSaude() throws Exception {
        // Initialize the database
        centroSaudeRepository.saveAndFlush(centroSaude);

        int databaseSizeBeforeUpdate = centroSaudeRepository.findAll().size();

        // Update the centroSaude
        CentroSaude updatedCentroSaude = centroSaudeRepository.findById(centroSaude.getId()).get();
        // Disconnect from session so that the updates on updatedCentroSaude are not directly saved in db
        em.detach(updatedCentroSaude);
        updatedCentroSaude
            .nome(UPDATED_NOME);

        restCentroSaudeMockMvc.perform(put("/api/centro-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCentroSaude)))
            .andExpect(status().isOk());

        // Validate the CentroSaude in the database
        List<CentroSaude> centroSaudeList = centroSaudeRepository.findAll();
        assertThat(centroSaudeList).hasSize(databaseSizeBeforeUpdate);
        CentroSaude testCentroSaude = centroSaudeList.get(centroSaudeList.size() - 1);
        assertThat(testCentroSaude.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingCentroSaude() throws Exception {
        int databaseSizeBeforeUpdate = centroSaudeRepository.findAll().size();

        // Create the CentroSaude

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroSaudeMockMvc.perform(put("/api/centro-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centroSaude)))
            .andExpect(status().isBadRequest());

        // Validate the CentroSaude in the database
        List<CentroSaude> centroSaudeList = centroSaudeRepository.findAll();
        assertThat(centroSaudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCentroSaude() throws Exception {
        // Initialize the database
        centroSaudeRepository.saveAndFlush(centroSaude);

        int databaseSizeBeforeDelete = centroSaudeRepository.findAll().size();

        // Delete the centroSaude
        restCentroSaudeMockMvc.perform(delete("/api/centro-saudes/{id}", centroSaude.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CentroSaude> centroSaudeList = centroSaudeRepository.findAll();
        assertThat(centroSaudeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
