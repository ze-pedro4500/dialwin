package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.DoenteSocioFamiliar;
import dw.dw.dw.repository.DoenteSocioFamiliarRepository;
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

import dw.dw.dw.domain.enumeration.Habilitacoes;
import dw.dw.dw.domain.enumeration.EstCivil;
/**
 * Integration tests for the {@link DoenteSocioFamiliarResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class DoenteSocioFamiliarResourceIT {

    private static final Habilitacoes DEFAULT_HABILITACOES = Habilitacoes.Iletrado;
    private static final Habilitacoes UPDATED_HABILITACOES = Habilitacoes.LeEscreve;

    private static final EstCivil DEFAULT_EST_CIVIL = EstCivil.Solteiro;
    private static final EstCivil UPDATED_EST_CIVIL = EstCivil.Casado;

    private static final String DEFAULT_CONJUGE_NOME = "AAAAAAAAAA";
    private static final String UPDATED_CONJUGE_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CONJUGE_PROF = "AAAAAAAAAA";
    private static final String UPDATED_CONJUGE_PROF = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIV_TEMP_LIV = "AAAAAAAAAA";
    private static final String UPDATED_ACTIV_TEMP_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_HABITACAO_OBS = "AAAAAAAAAA";
    private static final String UPDATED_HABITACAO_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_GRAU_CONFORTO_OBS = "AAAAAAAAAA";
    private static final String UPDATED_GRAU_CONFORTO_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_RESPOSTA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RESPOSTA_SOCIAL = "BBBBBBBBBB";

    @Autowired
    private DoenteSocioFamiliarRepository doenteSocioFamiliarRepository;

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

    private MockMvc restDoenteSocioFamiliarMockMvc;

    private DoenteSocioFamiliar doenteSocioFamiliar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoenteSocioFamiliarResource doenteSocioFamiliarResource = new DoenteSocioFamiliarResource(doenteSocioFamiliarRepository);
        this.restDoenteSocioFamiliarMockMvc = MockMvcBuilders.standaloneSetup(doenteSocioFamiliarResource)
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
    public static DoenteSocioFamiliar createEntity(EntityManager em) {
        DoenteSocioFamiliar doenteSocioFamiliar = new DoenteSocioFamiliar()
            .habilitacoes(DEFAULT_HABILITACOES)
            .estCivil(DEFAULT_EST_CIVIL)
            .conjugeNome(DEFAULT_CONJUGE_NOME)
            .conjugeProf(DEFAULT_CONJUGE_PROF)
            .activTempLiv(DEFAULT_ACTIV_TEMP_LIV)
            .habitacaoObs(DEFAULT_HABITACAO_OBS)
            .grauConfortoObs(DEFAULT_GRAU_CONFORTO_OBS)
            .respostaSocial(DEFAULT_RESPOSTA_SOCIAL);
        return doenteSocioFamiliar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoenteSocioFamiliar createUpdatedEntity(EntityManager em) {
        DoenteSocioFamiliar doenteSocioFamiliar = new DoenteSocioFamiliar()
            .habilitacoes(UPDATED_HABILITACOES)
            .estCivil(UPDATED_EST_CIVIL)
            .conjugeNome(UPDATED_CONJUGE_NOME)
            .conjugeProf(UPDATED_CONJUGE_PROF)
            .activTempLiv(UPDATED_ACTIV_TEMP_LIV)
            .habitacaoObs(UPDATED_HABITACAO_OBS)
            .grauConfortoObs(UPDATED_GRAU_CONFORTO_OBS)
            .respostaSocial(UPDATED_RESPOSTA_SOCIAL);
        return doenteSocioFamiliar;
    }

    @BeforeEach
    public void initTest() {
        doenteSocioFamiliar = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenteSocioFamiliar() throws Exception {
        int databaseSizeBeforeCreate = doenteSocioFamiliarRepository.findAll().size();

        // Create the DoenteSocioFamiliar
        restDoenteSocioFamiliarMockMvc.perform(post("/api/doente-socio-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteSocioFamiliar)))
            .andExpect(status().isCreated());

        // Validate the DoenteSocioFamiliar in the database
        List<DoenteSocioFamiliar> doenteSocioFamiliarList = doenteSocioFamiliarRepository.findAll();
        assertThat(doenteSocioFamiliarList).hasSize(databaseSizeBeforeCreate + 1);
        DoenteSocioFamiliar testDoenteSocioFamiliar = doenteSocioFamiliarList.get(doenteSocioFamiliarList.size() - 1);
        assertThat(testDoenteSocioFamiliar.getHabilitacoes()).isEqualTo(DEFAULT_HABILITACOES);
        assertThat(testDoenteSocioFamiliar.getEstCivil()).isEqualTo(DEFAULT_EST_CIVIL);
        assertThat(testDoenteSocioFamiliar.getConjugeNome()).isEqualTo(DEFAULT_CONJUGE_NOME);
        assertThat(testDoenteSocioFamiliar.getConjugeProf()).isEqualTo(DEFAULT_CONJUGE_PROF);
        assertThat(testDoenteSocioFamiliar.getActivTempLiv()).isEqualTo(DEFAULT_ACTIV_TEMP_LIV);
        assertThat(testDoenteSocioFamiliar.getHabitacaoObs()).isEqualTo(DEFAULT_HABITACAO_OBS);
        assertThat(testDoenteSocioFamiliar.getGrauConfortoObs()).isEqualTo(DEFAULT_GRAU_CONFORTO_OBS);
        assertThat(testDoenteSocioFamiliar.getRespostaSocial()).isEqualTo(DEFAULT_RESPOSTA_SOCIAL);
    }

    @Test
    @Transactional
    public void createDoenteSocioFamiliarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doenteSocioFamiliarRepository.findAll().size();

        // Create the DoenteSocioFamiliar with an existing ID
        doenteSocioFamiliar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoenteSocioFamiliarMockMvc.perform(post("/api/doente-socio-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteSocioFamiliar)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteSocioFamiliar in the database
        List<DoenteSocioFamiliar> doenteSocioFamiliarList = doenteSocioFamiliarRepository.findAll();
        assertThat(doenteSocioFamiliarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoenteSocioFamiliars() throws Exception {
        // Initialize the database
        doenteSocioFamiliarRepository.saveAndFlush(doenteSocioFamiliar);

        // Get all the doenteSocioFamiliarList
        restDoenteSocioFamiliarMockMvc.perform(get("/api/doente-socio-familiars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenteSocioFamiliar.getId().intValue())))
            .andExpect(jsonPath("$.[*].habilitacoes").value(hasItem(DEFAULT_HABILITACOES.toString())))
            .andExpect(jsonPath("$.[*].estCivil").value(hasItem(DEFAULT_EST_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].conjugeNome").value(hasItem(DEFAULT_CONJUGE_NOME)))
            .andExpect(jsonPath("$.[*].conjugeProf").value(hasItem(DEFAULT_CONJUGE_PROF)))
            .andExpect(jsonPath("$.[*].activTempLiv").value(hasItem(DEFAULT_ACTIV_TEMP_LIV)))
            .andExpect(jsonPath("$.[*].habitacaoObs").value(hasItem(DEFAULT_HABITACAO_OBS)))
            .andExpect(jsonPath("$.[*].grauConfortoObs").value(hasItem(DEFAULT_GRAU_CONFORTO_OBS)))
            .andExpect(jsonPath("$.[*].respostaSocial").value(hasItem(DEFAULT_RESPOSTA_SOCIAL)));
    }
    
    @Test
    @Transactional
    public void getDoenteSocioFamiliar() throws Exception {
        // Initialize the database
        doenteSocioFamiliarRepository.saveAndFlush(doenteSocioFamiliar);

        // Get the doenteSocioFamiliar
        restDoenteSocioFamiliarMockMvc.perform(get("/api/doente-socio-familiars/{id}", doenteSocioFamiliar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenteSocioFamiliar.getId().intValue()))
            .andExpect(jsonPath("$.habilitacoes").value(DEFAULT_HABILITACOES.toString()))
            .andExpect(jsonPath("$.estCivil").value(DEFAULT_EST_CIVIL.toString()))
            .andExpect(jsonPath("$.conjugeNome").value(DEFAULT_CONJUGE_NOME))
            .andExpect(jsonPath("$.conjugeProf").value(DEFAULT_CONJUGE_PROF))
            .andExpect(jsonPath("$.activTempLiv").value(DEFAULT_ACTIV_TEMP_LIV))
            .andExpect(jsonPath("$.habitacaoObs").value(DEFAULT_HABITACAO_OBS))
            .andExpect(jsonPath("$.grauConfortoObs").value(DEFAULT_GRAU_CONFORTO_OBS))
            .andExpect(jsonPath("$.respostaSocial").value(DEFAULT_RESPOSTA_SOCIAL));
    }

    @Test
    @Transactional
    public void getNonExistingDoenteSocioFamiliar() throws Exception {
        // Get the doenteSocioFamiliar
        restDoenteSocioFamiliarMockMvc.perform(get("/api/doente-socio-familiars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenteSocioFamiliar() throws Exception {
        // Initialize the database
        doenteSocioFamiliarRepository.saveAndFlush(doenteSocioFamiliar);

        int databaseSizeBeforeUpdate = doenteSocioFamiliarRepository.findAll().size();

        // Update the doenteSocioFamiliar
        DoenteSocioFamiliar updatedDoenteSocioFamiliar = doenteSocioFamiliarRepository.findById(doenteSocioFamiliar.getId()).get();
        // Disconnect from session so that the updates on updatedDoenteSocioFamiliar are not directly saved in db
        em.detach(updatedDoenteSocioFamiliar);
        updatedDoenteSocioFamiliar
            .habilitacoes(UPDATED_HABILITACOES)
            .estCivil(UPDATED_EST_CIVIL)
            .conjugeNome(UPDATED_CONJUGE_NOME)
            .conjugeProf(UPDATED_CONJUGE_PROF)
            .activTempLiv(UPDATED_ACTIV_TEMP_LIV)
            .habitacaoObs(UPDATED_HABITACAO_OBS)
            .grauConfortoObs(UPDATED_GRAU_CONFORTO_OBS)
            .respostaSocial(UPDATED_RESPOSTA_SOCIAL);

        restDoenteSocioFamiliarMockMvc.perform(put("/api/doente-socio-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenteSocioFamiliar)))
            .andExpect(status().isOk());

        // Validate the DoenteSocioFamiliar in the database
        List<DoenteSocioFamiliar> doenteSocioFamiliarList = doenteSocioFamiliarRepository.findAll();
        assertThat(doenteSocioFamiliarList).hasSize(databaseSizeBeforeUpdate);
        DoenteSocioFamiliar testDoenteSocioFamiliar = doenteSocioFamiliarList.get(doenteSocioFamiliarList.size() - 1);
        assertThat(testDoenteSocioFamiliar.getHabilitacoes()).isEqualTo(UPDATED_HABILITACOES);
        assertThat(testDoenteSocioFamiliar.getEstCivil()).isEqualTo(UPDATED_EST_CIVIL);
        assertThat(testDoenteSocioFamiliar.getConjugeNome()).isEqualTo(UPDATED_CONJUGE_NOME);
        assertThat(testDoenteSocioFamiliar.getConjugeProf()).isEqualTo(UPDATED_CONJUGE_PROF);
        assertThat(testDoenteSocioFamiliar.getActivTempLiv()).isEqualTo(UPDATED_ACTIV_TEMP_LIV);
        assertThat(testDoenteSocioFamiliar.getHabitacaoObs()).isEqualTo(UPDATED_HABITACAO_OBS);
        assertThat(testDoenteSocioFamiliar.getGrauConfortoObs()).isEqualTo(UPDATED_GRAU_CONFORTO_OBS);
        assertThat(testDoenteSocioFamiliar.getRespostaSocial()).isEqualTo(UPDATED_RESPOSTA_SOCIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenteSocioFamiliar() throws Exception {
        int databaseSizeBeforeUpdate = doenteSocioFamiliarRepository.findAll().size();

        // Create the DoenteSocioFamiliar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoenteSocioFamiliarMockMvc.perform(put("/api/doente-socio-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteSocioFamiliar)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteSocioFamiliar in the database
        List<DoenteSocioFamiliar> doenteSocioFamiliarList = doenteSocioFamiliarRepository.findAll();
        assertThat(doenteSocioFamiliarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenteSocioFamiliar() throws Exception {
        // Initialize the database
        doenteSocioFamiliarRepository.saveAndFlush(doenteSocioFamiliar);

        int databaseSizeBeforeDelete = doenteSocioFamiliarRepository.findAll().size();

        // Delete the doenteSocioFamiliar
        restDoenteSocioFamiliarMockMvc.perform(delete("/api/doente-socio-familiars/{id}", doenteSocioFamiliar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoenteSocioFamiliar> doenteSocioFamiliarList = doenteSocioFamiliarRepository.findAll();
        assertThat(doenteSocioFamiliarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
