package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.ACES;
import dw.dw.dw.repository.ACESRepository;
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
 * Integration tests for the {@link ACESResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class ACESResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ACESRepository aCESRepository;

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

    private MockMvc restACESMockMvc;

    private ACES aCES;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ACESResource aCESResource = new ACESResource(aCESRepository);
        this.restACESMockMvc = MockMvcBuilders.standaloneSetup(aCESResource)
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
    public static ACES createEntity(EntityManager em) {
        ACES aCES = new ACES()
            .nome(DEFAULT_NOME);
        return aCES;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ACES createUpdatedEntity(EntityManager em) {
        ACES aCES = new ACES()
            .nome(UPDATED_NOME);
        return aCES;
    }

    @BeforeEach
    public void initTest() {
        aCES = createEntity(em);
    }

    @Test
    @Transactional
    public void createACES() throws Exception {
        int databaseSizeBeforeCreate = aCESRepository.findAll().size();

        // Create the ACES
        restACESMockMvc.perform(post("/api/aces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCES)))
            .andExpect(status().isCreated());

        // Validate the ACES in the database
        List<ACES> aCESList = aCESRepository.findAll();
        assertThat(aCESList).hasSize(databaseSizeBeforeCreate + 1);
        ACES testACES = aCESList.get(aCESList.size() - 1);
        assertThat(testACES.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createACESWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aCESRepository.findAll().size();

        // Create the ACES with an existing ID
        aCES.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restACESMockMvc.perform(post("/api/aces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCES)))
            .andExpect(status().isBadRequest());

        // Validate the ACES in the database
        List<ACES> aCESList = aCESRepository.findAll();
        assertThat(aCESList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllACES() throws Exception {
        // Initialize the database
        aCESRepository.saveAndFlush(aCES);

        // Get all the aCESList
        restACESMockMvc.perform(get("/api/aces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aCES.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getACES() throws Exception {
        // Initialize the database
        aCESRepository.saveAndFlush(aCES);

        // Get the aCES
        restACESMockMvc.perform(get("/api/aces/{id}", aCES.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aCES.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingACES() throws Exception {
        // Get the aCES
        restACESMockMvc.perform(get("/api/aces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateACES() throws Exception {
        // Initialize the database
        aCESRepository.saveAndFlush(aCES);

        int databaseSizeBeforeUpdate = aCESRepository.findAll().size();

        // Update the aCES
        ACES updatedACES = aCESRepository.findById(aCES.getId()).get();
        // Disconnect from session so that the updates on updatedACES are not directly saved in db
        em.detach(updatedACES);
        updatedACES
            .nome(UPDATED_NOME);

        restACESMockMvc.perform(put("/api/aces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedACES)))
            .andExpect(status().isOk());

        // Validate the ACES in the database
        List<ACES> aCESList = aCESRepository.findAll();
        assertThat(aCESList).hasSize(databaseSizeBeforeUpdate);
        ACES testACES = aCESList.get(aCESList.size() - 1);
        assertThat(testACES.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingACES() throws Exception {
        int databaseSizeBeforeUpdate = aCESRepository.findAll().size();

        // Create the ACES

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restACESMockMvc.perform(put("/api/aces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aCES)))
            .andExpect(status().isBadRequest());

        // Validate the ACES in the database
        List<ACES> aCESList = aCESRepository.findAll();
        assertThat(aCESList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteACES() throws Exception {
        // Initialize the database
        aCESRepository.saveAndFlush(aCES);

        int databaseSizeBeforeDelete = aCESRepository.findAll().size();

        // Delete the aCES
        restACESMockMvc.perform(delete("/api/aces/{id}", aCES.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ACES> aCESList = aCESRepository.findAll();
        assertThat(aCESList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
