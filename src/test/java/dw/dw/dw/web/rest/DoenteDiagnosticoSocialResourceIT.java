package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.DoenteDiagnosticoSocial;
import dw.dw.dw.repository.DoenteDiagnosticoSocialRepository;
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
 * Integration tests for the {@link DoenteDiagnosticoSocialResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class DoenteDiagnosticoSocialResourceIT {

    private static final String DEFAULT_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_DESCR = "BBBBBBBBBB";

    @Autowired
    private DoenteDiagnosticoSocialRepository doenteDiagnosticoSocialRepository;

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

    private MockMvc restDoenteDiagnosticoSocialMockMvc;

    private DoenteDiagnosticoSocial doenteDiagnosticoSocial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoenteDiagnosticoSocialResource doenteDiagnosticoSocialResource = new DoenteDiagnosticoSocialResource(doenteDiagnosticoSocialRepository);
        this.restDoenteDiagnosticoSocialMockMvc = MockMvcBuilders.standaloneSetup(doenteDiagnosticoSocialResource)
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
    public static DoenteDiagnosticoSocial createEntity(EntityManager em) {
        DoenteDiagnosticoSocial doenteDiagnosticoSocial = new DoenteDiagnosticoSocial()
            .descr(DEFAULT_DESCR);
        return doenteDiagnosticoSocial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoenteDiagnosticoSocial createUpdatedEntity(EntityManager em) {
        DoenteDiagnosticoSocial doenteDiagnosticoSocial = new DoenteDiagnosticoSocial()
            .descr(UPDATED_DESCR);
        return doenteDiagnosticoSocial;
    }

    @BeforeEach
    public void initTest() {
        doenteDiagnosticoSocial = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenteDiagnosticoSocial() throws Exception {
        int databaseSizeBeforeCreate = doenteDiagnosticoSocialRepository.findAll().size();

        // Create the DoenteDiagnosticoSocial
        restDoenteDiagnosticoSocialMockMvc.perform(post("/api/doente-diagnostico-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteDiagnosticoSocial)))
            .andExpect(status().isCreated());

        // Validate the DoenteDiagnosticoSocial in the database
        List<DoenteDiagnosticoSocial> doenteDiagnosticoSocialList = doenteDiagnosticoSocialRepository.findAll();
        assertThat(doenteDiagnosticoSocialList).hasSize(databaseSizeBeforeCreate + 1);
        DoenteDiagnosticoSocial testDoenteDiagnosticoSocial = doenteDiagnosticoSocialList.get(doenteDiagnosticoSocialList.size() - 1);
        assertThat(testDoenteDiagnosticoSocial.getDescr()).isEqualTo(DEFAULT_DESCR);
    }

    @Test
    @Transactional
    public void createDoenteDiagnosticoSocialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doenteDiagnosticoSocialRepository.findAll().size();

        // Create the DoenteDiagnosticoSocial with an existing ID
        doenteDiagnosticoSocial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoenteDiagnosticoSocialMockMvc.perform(post("/api/doente-diagnostico-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteDiagnosticoSocial)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteDiagnosticoSocial in the database
        List<DoenteDiagnosticoSocial> doenteDiagnosticoSocialList = doenteDiagnosticoSocialRepository.findAll();
        assertThat(doenteDiagnosticoSocialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoenteDiagnosticoSocials() throws Exception {
        // Initialize the database
        doenteDiagnosticoSocialRepository.saveAndFlush(doenteDiagnosticoSocial);

        // Get all the doenteDiagnosticoSocialList
        restDoenteDiagnosticoSocialMockMvc.perform(get("/api/doente-diagnostico-socials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenteDiagnosticoSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].descr").value(hasItem(DEFAULT_DESCR)));
    }
    
    @Test
    @Transactional
    public void getDoenteDiagnosticoSocial() throws Exception {
        // Initialize the database
        doenteDiagnosticoSocialRepository.saveAndFlush(doenteDiagnosticoSocial);

        // Get the doenteDiagnosticoSocial
        restDoenteDiagnosticoSocialMockMvc.perform(get("/api/doente-diagnostico-socials/{id}", doenteDiagnosticoSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenteDiagnosticoSocial.getId().intValue()))
            .andExpect(jsonPath("$.descr").value(DEFAULT_DESCR));
    }

    @Test
    @Transactional
    public void getNonExistingDoenteDiagnosticoSocial() throws Exception {
        // Get the doenteDiagnosticoSocial
        restDoenteDiagnosticoSocialMockMvc.perform(get("/api/doente-diagnostico-socials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenteDiagnosticoSocial() throws Exception {
        // Initialize the database
        doenteDiagnosticoSocialRepository.saveAndFlush(doenteDiagnosticoSocial);

        int databaseSizeBeforeUpdate = doenteDiagnosticoSocialRepository.findAll().size();

        // Update the doenteDiagnosticoSocial
        DoenteDiagnosticoSocial updatedDoenteDiagnosticoSocial = doenteDiagnosticoSocialRepository.findById(doenteDiagnosticoSocial.getId()).get();
        // Disconnect from session so that the updates on updatedDoenteDiagnosticoSocial are not directly saved in db
        em.detach(updatedDoenteDiagnosticoSocial);
        updatedDoenteDiagnosticoSocial
            .descr(UPDATED_DESCR);

        restDoenteDiagnosticoSocialMockMvc.perform(put("/api/doente-diagnostico-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenteDiagnosticoSocial)))
            .andExpect(status().isOk());

        // Validate the DoenteDiagnosticoSocial in the database
        List<DoenteDiagnosticoSocial> doenteDiagnosticoSocialList = doenteDiagnosticoSocialRepository.findAll();
        assertThat(doenteDiagnosticoSocialList).hasSize(databaseSizeBeforeUpdate);
        DoenteDiagnosticoSocial testDoenteDiagnosticoSocial = doenteDiagnosticoSocialList.get(doenteDiagnosticoSocialList.size() - 1);
        assertThat(testDoenteDiagnosticoSocial.getDescr()).isEqualTo(UPDATED_DESCR);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenteDiagnosticoSocial() throws Exception {
        int databaseSizeBeforeUpdate = doenteDiagnosticoSocialRepository.findAll().size();

        // Create the DoenteDiagnosticoSocial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoenteDiagnosticoSocialMockMvc.perform(put("/api/doente-diagnostico-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteDiagnosticoSocial)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteDiagnosticoSocial in the database
        List<DoenteDiagnosticoSocial> doenteDiagnosticoSocialList = doenteDiagnosticoSocialRepository.findAll();
        assertThat(doenteDiagnosticoSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenteDiagnosticoSocial() throws Exception {
        // Initialize the database
        doenteDiagnosticoSocialRepository.saveAndFlush(doenteDiagnosticoSocial);

        int databaseSizeBeforeDelete = doenteDiagnosticoSocialRepository.findAll().size();

        // Delete the doenteDiagnosticoSocial
        restDoenteDiagnosticoSocialMockMvc.perform(delete("/api/doente-diagnostico-socials/{id}", doenteDiagnosticoSocial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoenteDiagnosticoSocial> doenteDiagnosticoSocialList = doenteDiagnosticoSocialRepository.findAll();
        assertThat(doenteDiagnosticoSocialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
