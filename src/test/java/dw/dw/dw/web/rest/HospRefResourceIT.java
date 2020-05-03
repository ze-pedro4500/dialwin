package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.HospRef;
import dw.dw.dw.repository.HospRefRepository;
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
 * Integration tests for the {@link HospRefResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class HospRefResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private HospRefRepository hospRefRepository;

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

    private MockMvc restHospRefMockMvc;

    private HospRef hospRef;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HospRefResource hospRefResource = new HospRefResource(hospRefRepository);
        this.restHospRefMockMvc = MockMvcBuilders.standaloneSetup(hospRefResource)
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
    public static HospRef createEntity(EntityManager em) {
        HospRef hospRef = new HospRef()
            .nome(DEFAULT_NOME);
        return hospRef;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HospRef createUpdatedEntity(EntityManager em) {
        HospRef hospRef = new HospRef()
            .nome(UPDATED_NOME);
        return hospRef;
    }

    @BeforeEach
    public void initTest() {
        hospRef = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospRef() throws Exception {
        int databaseSizeBeforeCreate = hospRefRepository.findAll().size();

        // Create the HospRef
        restHospRefMockMvc.perform(post("/api/hosp-refs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospRef)))
            .andExpect(status().isCreated());

        // Validate the HospRef in the database
        List<HospRef> hospRefList = hospRefRepository.findAll();
        assertThat(hospRefList).hasSize(databaseSizeBeforeCreate + 1);
        HospRef testHospRef = hospRefList.get(hospRefList.size() - 1);
        assertThat(testHospRef.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createHospRefWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospRefRepository.findAll().size();

        // Create the HospRef with an existing ID
        hospRef.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospRefMockMvc.perform(post("/api/hosp-refs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospRef)))
            .andExpect(status().isBadRequest());

        // Validate the HospRef in the database
        List<HospRef> hospRefList = hospRefRepository.findAll();
        assertThat(hospRefList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHospRefs() throws Exception {
        // Initialize the database
        hospRefRepository.saveAndFlush(hospRef);

        // Get all the hospRefList
        restHospRefMockMvc.perform(get("/api/hosp-refs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getHospRef() throws Exception {
        // Initialize the database
        hospRefRepository.saveAndFlush(hospRef);

        // Get the hospRef
        restHospRefMockMvc.perform(get("/api/hosp-refs/{id}", hospRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hospRef.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingHospRef() throws Exception {
        // Get the hospRef
        restHospRefMockMvc.perform(get("/api/hosp-refs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospRef() throws Exception {
        // Initialize the database
        hospRefRepository.saveAndFlush(hospRef);

        int databaseSizeBeforeUpdate = hospRefRepository.findAll().size();

        // Update the hospRef
        HospRef updatedHospRef = hospRefRepository.findById(hospRef.getId()).get();
        // Disconnect from session so that the updates on updatedHospRef are not directly saved in db
        em.detach(updatedHospRef);
        updatedHospRef
            .nome(UPDATED_NOME);

        restHospRefMockMvc.perform(put("/api/hosp-refs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHospRef)))
            .andExpect(status().isOk());

        // Validate the HospRef in the database
        List<HospRef> hospRefList = hospRefRepository.findAll();
        assertThat(hospRefList).hasSize(databaseSizeBeforeUpdate);
        HospRef testHospRef = hospRefList.get(hospRefList.size() - 1);
        assertThat(testHospRef.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingHospRef() throws Exception {
        int databaseSizeBeforeUpdate = hospRefRepository.findAll().size();

        // Create the HospRef

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospRefMockMvc.perform(put("/api/hosp-refs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospRef)))
            .andExpect(status().isBadRequest());

        // Validate the HospRef in the database
        List<HospRef> hospRefList = hospRefRepository.findAll();
        assertThat(hospRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospRef() throws Exception {
        // Initialize the database
        hospRefRepository.saveAndFlush(hospRef);

        int databaseSizeBeforeDelete = hospRefRepository.findAll().size();

        // Delete the hospRef
        restHospRefMockMvc.perform(delete("/api/hosp-refs/{id}", hospRef.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HospRef> hospRefList = hospRefRepository.findAll();
        assertThat(hospRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
