package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.SubSistemas;
import dw.dw.dw.repository.SubSistemasRepository;
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
 * Integration tests for the {@link SubSistemasResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class SubSistemasResourceIT {

    private static final String DEFAULT_GID_NOME = "AAAAAAAAAA";
    private static final String UPDATED_GID_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GID_CODE = 1;
    private static final Integer UPDATED_GID_CODE = 2;

    @Autowired
    private SubSistemasRepository subSistemasRepository;

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

    private MockMvc restSubSistemasMockMvc;

    private SubSistemas subSistemas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubSistemasResource subSistemasResource = new SubSistemasResource(subSistemasRepository);
        this.restSubSistemasMockMvc = MockMvcBuilders.standaloneSetup(subSistemasResource)
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
    public static SubSistemas createEntity(EntityManager em) {
        SubSistemas subSistemas = new SubSistemas()
            .gidNome(DEFAULT_GID_NOME)
            .gidCode(DEFAULT_GID_CODE);
        return subSistemas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubSistemas createUpdatedEntity(EntityManager em) {
        SubSistemas subSistemas = new SubSistemas()
            .gidNome(UPDATED_GID_NOME)
            .gidCode(UPDATED_GID_CODE);
        return subSistemas;
    }

    @BeforeEach
    public void initTest() {
        subSistemas = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubSistemas() throws Exception {
        int databaseSizeBeforeCreate = subSistemasRepository.findAll().size();

        // Create the SubSistemas
        restSubSistemasMockMvc.perform(post("/api/sub-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subSistemas)))
            .andExpect(status().isCreated());

        // Validate the SubSistemas in the database
        List<SubSistemas> subSistemasList = subSistemasRepository.findAll();
        assertThat(subSistemasList).hasSize(databaseSizeBeforeCreate + 1);
        SubSistemas testSubSistemas = subSistemasList.get(subSistemasList.size() - 1);
        assertThat(testSubSistemas.getGidNome()).isEqualTo(DEFAULT_GID_NOME);
        assertThat(testSubSistemas.getGidCode()).isEqualTo(DEFAULT_GID_CODE);
    }

    @Test
    @Transactional
    public void createSubSistemasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subSistemasRepository.findAll().size();

        // Create the SubSistemas with an existing ID
        subSistemas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubSistemasMockMvc.perform(post("/api/sub-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subSistemas)))
            .andExpect(status().isBadRequest());

        // Validate the SubSistemas in the database
        List<SubSistemas> subSistemasList = subSistemasRepository.findAll();
        assertThat(subSistemasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubSistemas() throws Exception {
        // Initialize the database
        subSistemasRepository.saveAndFlush(subSistemas);

        // Get all the subSistemasList
        restSubSistemasMockMvc.perform(get("/api/sub-sistemas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subSistemas.getId().intValue())))
            .andExpect(jsonPath("$.[*].gidNome").value(hasItem(DEFAULT_GID_NOME)))
            .andExpect(jsonPath("$.[*].gidCode").value(hasItem(DEFAULT_GID_CODE)));
    }
    
    @Test
    @Transactional
    public void getSubSistemas() throws Exception {
        // Initialize the database
        subSistemasRepository.saveAndFlush(subSistemas);

        // Get the subSistemas
        restSubSistemasMockMvc.perform(get("/api/sub-sistemas/{id}", subSistemas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subSistemas.getId().intValue()))
            .andExpect(jsonPath("$.gidNome").value(DEFAULT_GID_NOME))
            .andExpect(jsonPath("$.gidCode").value(DEFAULT_GID_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingSubSistemas() throws Exception {
        // Get the subSistemas
        restSubSistemasMockMvc.perform(get("/api/sub-sistemas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubSistemas() throws Exception {
        // Initialize the database
        subSistemasRepository.saveAndFlush(subSistemas);

        int databaseSizeBeforeUpdate = subSistemasRepository.findAll().size();

        // Update the subSistemas
        SubSistemas updatedSubSistemas = subSistemasRepository.findById(subSistemas.getId()).get();
        // Disconnect from session so that the updates on updatedSubSistemas are not directly saved in db
        em.detach(updatedSubSistemas);
        updatedSubSistemas
            .gidNome(UPDATED_GID_NOME)
            .gidCode(UPDATED_GID_CODE);

        restSubSistemasMockMvc.perform(put("/api/sub-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubSistemas)))
            .andExpect(status().isOk());

        // Validate the SubSistemas in the database
        List<SubSistemas> subSistemasList = subSistemasRepository.findAll();
        assertThat(subSistemasList).hasSize(databaseSizeBeforeUpdate);
        SubSistemas testSubSistemas = subSistemasList.get(subSistemasList.size() - 1);
        assertThat(testSubSistemas.getGidNome()).isEqualTo(UPDATED_GID_NOME);
        assertThat(testSubSistemas.getGidCode()).isEqualTo(UPDATED_GID_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubSistemas() throws Exception {
        int databaseSizeBeforeUpdate = subSistemasRepository.findAll().size();

        // Create the SubSistemas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubSistemasMockMvc.perform(put("/api/sub-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subSistemas)))
            .andExpect(status().isBadRequest());

        // Validate the SubSistemas in the database
        List<SubSistemas> subSistemasList = subSistemasRepository.findAll();
        assertThat(subSistemasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubSistemas() throws Exception {
        // Initialize the database
        subSistemasRepository.saveAndFlush(subSistemas);

        int databaseSizeBeforeDelete = subSistemasRepository.findAll().size();

        // Delete the subSistemas
        restSubSistemasMockMvc.perform(delete("/api/sub-sistemas/{id}", subSistemas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubSistemas> subSistemasList = subSistemasRepository.findAll();
        assertThat(subSistemasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
