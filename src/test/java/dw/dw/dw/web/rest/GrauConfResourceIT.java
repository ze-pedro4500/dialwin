package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.GrauConf;
import dw.dw.dw.repository.GrauConfRepository;
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
 * Integration tests for the {@link GrauConfResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class GrauConfResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private GrauConfRepository grauConfRepository;

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

    private MockMvc restGrauConfMockMvc;

    private GrauConf grauConf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrauConfResource grauConfResource = new GrauConfResource(grauConfRepository);
        this.restGrauConfMockMvc = MockMvcBuilders.standaloneSetup(grauConfResource)
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
    public static GrauConf createEntity(EntityManager em) {
        GrauConf grauConf = new GrauConf()
            .nome(DEFAULT_NOME);
        return grauConf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrauConf createUpdatedEntity(EntityManager em) {
        GrauConf grauConf = new GrauConf()
            .nome(UPDATED_NOME);
        return grauConf;
    }

    @BeforeEach
    public void initTest() {
        grauConf = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrauConf() throws Exception {
        int databaseSizeBeforeCreate = grauConfRepository.findAll().size();

        // Create the GrauConf
        restGrauConfMockMvc.perform(post("/api/grau-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grauConf)))
            .andExpect(status().isCreated());

        // Validate the GrauConf in the database
        List<GrauConf> grauConfList = grauConfRepository.findAll();
        assertThat(grauConfList).hasSize(databaseSizeBeforeCreate + 1);
        GrauConf testGrauConf = grauConfList.get(grauConfList.size() - 1);
        assertThat(testGrauConf.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createGrauConfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grauConfRepository.findAll().size();

        // Create the GrauConf with an existing ID
        grauConf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrauConfMockMvc.perform(post("/api/grau-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grauConf)))
            .andExpect(status().isBadRequest());

        // Validate the GrauConf in the database
        List<GrauConf> grauConfList = grauConfRepository.findAll();
        assertThat(grauConfList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGrauConfs() throws Exception {
        // Initialize the database
        grauConfRepository.saveAndFlush(grauConf);

        // Get all the grauConfList
        restGrauConfMockMvc.perform(get("/api/grau-confs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grauConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getGrauConf() throws Exception {
        // Initialize the database
        grauConfRepository.saveAndFlush(grauConf);

        // Get the grauConf
        restGrauConfMockMvc.perform(get("/api/grau-confs/{id}", grauConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grauConf.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingGrauConf() throws Exception {
        // Get the grauConf
        restGrauConfMockMvc.perform(get("/api/grau-confs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrauConf() throws Exception {
        // Initialize the database
        grauConfRepository.saveAndFlush(grauConf);

        int databaseSizeBeforeUpdate = grauConfRepository.findAll().size();

        // Update the grauConf
        GrauConf updatedGrauConf = grauConfRepository.findById(grauConf.getId()).get();
        // Disconnect from session so that the updates on updatedGrauConf are not directly saved in db
        em.detach(updatedGrauConf);
        updatedGrauConf
            .nome(UPDATED_NOME);

        restGrauConfMockMvc.perform(put("/api/grau-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrauConf)))
            .andExpect(status().isOk());

        // Validate the GrauConf in the database
        List<GrauConf> grauConfList = grauConfRepository.findAll();
        assertThat(grauConfList).hasSize(databaseSizeBeforeUpdate);
        GrauConf testGrauConf = grauConfList.get(grauConfList.size() - 1);
        assertThat(testGrauConf.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingGrauConf() throws Exception {
        int databaseSizeBeforeUpdate = grauConfRepository.findAll().size();

        // Create the GrauConf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrauConfMockMvc.perform(put("/api/grau-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grauConf)))
            .andExpect(status().isBadRequest());

        // Validate the GrauConf in the database
        List<GrauConf> grauConfList = grauConfRepository.findAll();
        assertThat(grauConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrauConf() throws Exception {
        // Initialize the database
        grauConfRepository.saveAndFlush(grauConf);

        int databaseSizeBeforeDelete = grauConfRepository.findAll().size();

        // Delete the grauConf
        restGrauConfMockMvc.perform(delete("/api/grau-confs/{id}", grauConf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrauConf> grauConfList = grauConfRepository.findAll();
        assertThat(grauConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
