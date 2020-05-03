package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.DoenteContactos;
import dw.dw.dw.repository.DoenteContactosRepository;
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
 * Integration tests for the {@link DoenteContactosResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class DoenteContactosResourceIT {

    private static final String DEFAULT_TRANSPORTADOR = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORTADOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEF_TRANSP = 1;
    private static final Integer UPDATED_TELEF_TRANSP = 2;

    @Autowired
    private DoenteContactosRepository doenteContactosRepository;

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

    private MockMvc restDoenteContactosMockMvc;

    private DoenteContactos doenteContactos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoenteContactosResource doenteContactosResource = new DoenteContactosResource(doenteContactosRepository);
        this.restDoenteContactosMockMvc = MockMvcBuilders.standaloneSetup(doenteContactosResource)
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
    public static DoenteContactos createEntity(EntityManager em) {
        DoenteContactos doenteContactos = new DoenteContactos()
            .transportador(DEFAULT_TRANSPORTADOR)
            .telefTransp(DEFAULT_TELEF_TRANSP);
        return doenteContactos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoenteContactos createUpdatedEntity(EntityManager em) {
        DoenteContactos doenteContactos = new DoenteContactos()
            .transportador(UPDATED_TRANSPORTADOR)
            .telefTransp(UPDATED_TELEF_TRANSP);
        return doenteContactos;
    }

    @BeforeEach
    public void initTest() {
        doenteContactos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenteContactos() throws Exception {
        int databaseSizeBeforeCreate = doenteContactosRepository.findAll().size();

        // Create the DoenteContactos
        restDoenteContactosMockMvc.perform(post("/api/doente-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteContactos)))
            .andExpect(status().isCreated());

        // Validate the DoenteContactos in the database
        List<DoenteContactos> doenteContactosList = doenteContactosRepository.findAll();
        assertThat(doenteContactosList).hasSize(databaseSizeBeforeCreate + 1);
        DoenteContactos testDoenteContactos = doenteContactosList.get(doenteContactosList.size() - 1);
        assertThat(testDoenteContactos.getTransportador()).isEqualTo(DEFAULT_TRANSPORTADOR);
        assertThat(testDoenteContactos.getTelefTransp()).isEqualTo(DEFAULT_TELEF_TRANSP);
    }

    @Test
    @Transactional
    public void createDoenteContactosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doenteContactosRepository.findAll().size();

        // Create the DoenteContactos with an existing ID
        doenteContactos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoenteContactosMockMvc.perform(post("/api/doente-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteContactos)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteContactos in the database
        List<DoenteContactos> doenteContactosList = doenteContactosRepository.findAll();
        assertThat(doenteContactosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoenteContactos() throws Exception {
        // Initialize the database
        doenteContactosRepository.saveAndFlush(doenteContactos);

        // Get all the doenteContactosList
        restDoenteContactosMockMvc.perform(get("/api/doente-contactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenteContactos.getId().intValue())))
            .andExpect(jsonPath("$.[*].transportador").value(hasItem(DEFAULT_TRANSPORTADOR)))
            .andExpect(jsonPath("$.[*].telefTransp").value(hasItem(DEFAULT_TELEF_TRANSP)));
    }
    
    @Test
    @Transactional
    public void getDoenteContactos() throws Exception {
        // Initialize the database
        doenteContactosRepository.saveAndFlush(doenteContactos);

        // Get the doenteContactos
        restDoenteContactosMockMvc.perform(get("/api/doente-contactos/{id}", doenteContactos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenteContactos.getId().intValue()))
            .andExpect(jsonPath("$.transportador").value(DEFAULT_TRANSPORTADOR))
            .andExpect(jsonPath("$.telefTransp").value(DEFAULT_TELEF_TRANSP));
    }

    @Test
    @Transactional
    public void getNonExistingDoenteContactos() throws Exception {
        // Get the doenteContactos
        restDoenteContactosMockMvc.perform(get("/api/doente-contactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenteContactos() throws Exception {
        // Initialize the database
        doenteContactosRepository.saveAndFlush(doenteContactos);

        int databaseSizeBeforeUpdate = doenteContactosRepository.findAll().size();

        // Update the doenteContactos
        DoenteContactos updatedDoenteContactos = doenteContactosRepository.findById(doenteContactos.getId()).get();
        // Disconnect from session so that the updates on updatedDoenteContactos are not directly saved in db
        em.detach(updatedDoenteContactos);
        updatedDoenteContactos
            .transportador(UPDATED_TRANSPORTADOR)
            .telefTransp(UPDATED_TELEF_TRANSP);

        restDoenteContactosMockMvc.perform(put("/api/doente-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenteContactos)))
            .andExpect(status().isOk());

        // Validate the DoenteContactos in the database
        List<DoenteContactos> doenteContactosList = doenteContactosRepository.findAll();
        assertThat(doenteContactosList).hasSize(databaseSizeBeforeUpdate);
        DoenteContactos testDoenteContactos = doenteContactosList.get(doenteContactosList.size() - 1);
        assertThat(testDoenteContactos.getTransportador()).isEqualTo(UPDATED_TRANSPORTADOR);
        assertThat(testDoenteContactos.getTelefTransp()).isEqualTo(UPDATED_TELEF_TRANSP);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenteContactos() throws Exception {
        int databaseSizeBeforeUpdate = doenteContactosRepository.findAll().size();

        // Create the DoenteContactos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoenteContactosMockMvc.perform(put("/api/doente-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteContactos)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteContactos in the database
        List<DoenteContactos> doenteContactosList = doenteContactosRepository.findAll();
        assertThat(doenteContactosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenteContactos() throws Exception {
        // Initialize the database
        doenteContactosRepository.saveAndFlush(doenteContactos);

        int databaseSizeBeforeDelete = doenteContactosRepository.findAll().size();

        // Delete the doenteContactos
        restDoenteContactosMockMvc.perform(delete("/api/doente-contactos/{id}", doenteContactos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoenteContactos> doenteContactosList = doenteContactosRepository.findAll();
        assertThat(doenteContactosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
