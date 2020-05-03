package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.DoenteIdentidade;
import dw.dw.dw.repository.DoenteIdentidadeRepository;
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

import dw.dw.dw.domain.enumeration.Sexo;
/**
 * Integration tests for the {@link DoenteIdentidadeResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)
public class DoenteIdentidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASC = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ALTURA = 1;
    private static final Integer UPDATED_ALTURA = 2;

    private static final String DEFAULT_MORADA = "AAAAAAAAAA";
    private static final String UPDATED_MORADA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_POST = "AAAAAAAAAA";
    private static final String UPDATED_COD_POST = "BBBBBBBBBB";

    private static final String DEFAULT_FREGUESIA = "AAAAAAAAAA";
    private static final String UPDATED_FREGUESIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIF = 1;
    private static final Integer UPDATED_NIF = 2;

    private static final String DEFAULT_MED_FAM = "AAAAAAAAAA";
    private static final String UPDATED_MED_FAM = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.Masculino;
    private static final Sexo UPDATED_SEXO = Sexo.Feminino;

    private static final Integer DEFAULT_TELEF = 1;
    private static final Integer UPDATED_TELEF = 2;

    private static final Integer DEFAULT_TELEF_2 = 1;
    private static final Integer UPDATED_TELEF_2 = 2;

    private static final Integer DEFAULT_DOC_ID = 1;
    private static final Integer UPDATED_DOC_ID = 2;

    private static final Integer DEFAULT_N_BENEF = 1;
    private static final Integer UPDATED_N_BENEF = 2;

    private static final Integer DEFAULT_N_UTENTE = 1;
    private static final Integer UPDATED_N_UTENTE = 2;

    private static final Integer DEFAULT_NUM_PROC_HOSP = 1;
    private static final Integer UPDATED_NUM_PROC_HOSP = 2;

    @Autowired
    private DoenteIdentidadeRepository doenteIdentidadeRepository;

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

    private MockMvc restDoenteIdentidadeMockMvc;

    private DoenteIdentidade doenteIdentidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoenteIdentidadeResource doenteIdentidadeResource = new DoenteIdentidadeResource(doenteIdentidadeRepository);
        this.restDoenteIdentidadeMockMvc = MockMvcBuilders.standaloneSetup(doenteIdentidadeResource)
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
    public static DoenteIdentidade createEntity(EntityManager em) {
        DoenteIdentidade doenteIdentidade = new DoenteIdentidade()
            .nome(DEFAULT_NOME)
            .dataNasc(DEFAULT_DATA_NASC)
            .altura(DEFAULT_ALTURA)
            .morada(DEFAULT_MORADA)
            .codPost(DEFAULT_COD_POST)
            .freguesia(DEFAULT_FREGUESIA)
            .nif(DEFAULT_NIF)
            .medFam(DEFAULT_MED_FAM)
            .sexo(DEFAULT_SEXO)
            .telef(DEFAULT_TELEF)
            .telef2(DEFAULT_TELEF_2)
            .docId(DEFAULT_DOC_ID)
            .nBenef(DEFAULT_N_BENEF)
            .nUtente(DEFAULT_N_UTENTE)
            .numProcHosp(DEFAULT_NUM_PROC_HOSP);
        return doenteIdentidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoenteIdentidade createUpdatedEntity(EntityManager em) {
        DoenteIdentidade doenteIdentidade = new DoenteIdentidade()
            .nome(UPDATED_NOME)
            .dataNasc(UPDATED_DATA_NASC)
            .altura(UPDATED_ALTURA)
            .morada(UPDATED_MORADA)
            .codPost(UPDATED_COD_POST)
            .freguesia(UPDATED_FREGUESIA)
            .nif(UPDATED_NIF)
            .medFam(UPDATED_MED_FAM)
            .sexo(UPDATED_SEXO)
            .telef(UPDATED_TELEF)
            .telef2(UPDATED_TELEF_2)
            .docId(UPDATED_DOC_ID)
            .nBenef(UPDATED_N_BENEF)
            .nUtente(UPDATED_N_UTENTE)
            .numProcHosp(UPDATED_NUM_PROC_HOSP);
        return doenteIdentidade;
    }

    @BeforeEach
    public void initTest() {
        doenteIdentidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenteIdentidade() throws Exception {
        int databaseSizeBeforeCreate = doenteIdentidadeRepository.findAll().size();

        // Create the DoenteIdentidade
        restDoenteIdentidadeMockMvc.perform(post("/api/doente-identidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteIdentidade)))
            .andExpect(status().isCreated());

        // Validate the DoenteIdentidade in the database
        List<DoenteIdentidade> doenteIdentidadeList = doenteIdentidadeRepository.findAll();
        assertThat(doenteIdentidadeList).hasSize(databaseSizeBeforeCreate + 1);
        DoenteIdentidade testDoenteIdentidade = doenteIdentidadeList.get(doenteIdentidadeList.size() - 1);
        assertThat(testDoenteIdentidade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDoenteIdentidade.getDataNasc()).isEqualTo(DEFAULT_DATA_NASC);
        assertThat(testDoenteIdentidade.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDoenteIdentidade.getMorada()).isEqualTo(DEFAULT_MORADA);
        assertThat(testDoenteIdentidade.getCodPost()).isEqualTo(DEFAULT_COD_POST);
        assertThat(testDoenteIdentidade.getFreguesia()).isEqualTo(DEFAULT_FREGUESIA);
        assertThat(testDoenteIdentidade.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testDoenteIdentidade.getMedFam()).isEqualTo(DEFAULT_MED_FAM);
        assertThat(testDoenteIdentidade.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testDoenteIdentidade.getTelef()).isEqualTo(DEFAULT_TELEF);
        assertThat(testDoenteIdentidade.getTelef2()).isEqualTo(DEFAULT_TELEF_2);
        assertThat(testDoenteIdentidade.getDocId()).isEqualTo(DEFAULT_DOC_ID);
        assertThat(testDoenteIdentidade.getnBenef()).isEqualTo(DEFAULT_N_BENEF);
        assertThat(testDoenteIdentidade.getnUtente()).isEqualTo(DEFAULT_N_UTENTE);
        assertThat(testDoenteIdentidade.getNumProcHosp()).isEqualTo(DEFAULT_NUM_PROC_HOSP);
    }

    @Test
    @Transactional
    public void createDoenteIdentidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doenteIdentidadeRepository.findAll().size();

        // Create the DoenteIdentidade with an existing ID
        doenteIdentidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoenteIdentidadeMockMvc.perform(post("/api/doente-identidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteIdentidade)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteIdentidade in the database
        List<DoenteIdentidade> doenteIdentidadeList = doenteIdentidadeRepository.findAll();
        assertThat(doenteIdentidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoenteIdentidades() throws Exception {
        // Initialize the database
        doenteIdentidadeRepository.saveAndFlush(doenteIdentidade);

        // Get all the doenteIdentidadeList
        restDoenteIdentidadeMockMvc.perform(get("/api/doente-identidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenteIdentidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNasc").value(hasItem(DEFAULT_DATA_NASC.toString())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].morada").value(hasItem(DEFAULT_MORADA)))
            .andExpect(jsonPath("$.[*].codPost").value(hasItem(DEFAULT_COD_POST)))
            .andExpect(jsonPath("$.[*].freguesia").value(hasItem(DEFAULT_FREGUESIA)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].medFam").value(hasItem(DEFAULT_MED_FAM)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].telef").value(hasItem(DEFAULT_TELEF)))
            .andExpect(jsonPath("$.[*].telef2").value(hasItem(DEFAULT_TELEF_2)))
            .andExpect(jsonPath("$.[*].docId").value(hasItem(DEFAULT_DOC_ID)))
            .andExpect(jsonPath("$.[*].nBenef").value(hasItem(DEFAULT_N_BENEF)))
            .andExpect(jsonPath("$.[*].nUtente").value(hasItem(DEFAULT_N_UTENTE)))
            .andExpect(jsonPath("$.[*].numProcHosp").value(hasItem(DEFAULT_NUM_PROC_HOSP)));
    }
    
    @Test
    @Transactional
    public void getDoenteIdentidade() throws Exception {
        // Initialize the database
        doenteIdentidadeRepository.saveAndFlush(doenteIdentidade);

        // Get the doenteIdentidade
        restDoenteIdentidadeMockMvc.perform(get("/api/doente-identidades/{id}", doenteIdentidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenteIdentidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNasc").value(DEFAULT_DATA_NASC.toString()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA))
            .andExpect(jsonPath("$.morada").value(DEFAULT_MORADA))
            .andExpect(jsonPath("$.codPost").value(DEFAULT_COD_POST))
            .andExpect(jsonPath("$.freguesia").value(DEFAULT_FREGUESIA))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.medFam").value(DEFAULT_MED_FAM))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.telef").value(DEFAULT_TELEF))
            .andExpect(jsonPath("$.telef2").value(DEFAULT_TELEF_2))
            .andExpect(jsonPath("$.docId").value(DEFAULT_DOC_ID))
            .andExpect(jsonPath("$.nBenef").value(DEFAULT_N_BENEF))
            .andExpect(jsonPath("$.nUtente").value(DEFAULT_N_UTENTE))
            .andExpect(jsonPath("$.numProcHosp").value(DEFAULT_NUM_PROC_HOSP));
    }

    @Test
    @Transactional
    public void getNonExistingDoenteIdentidade() throws Exception {
        // Get the doenteIdentidade
        restDoenteIdentidadeMockMvc.perform(get("/api/doente-identidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenteIdentidade() throws Exception {
        // Initialize the database
        doenteIdentidadeRepository.saveAndFlush(doenteIdentidade);

        int databaseSizeBeforeUpdate = doenteIdentidadeRepository.findAll().size();

        // Update the doenteIdentidade
        DoenteIdentidade updatedDoenteIdentidade = doenteIdentidadeRepository.findById(doenteIdentidade.getId()).get();
        // Disconnect from session so that the updates on updatedDoenteIdentidade are not directly saved in db
        em.detach(updatedDoenteIdentidade);
        updatedDoenteIdentidade
            .nome(UPDATED_NOME)
            .dataNasc(UPDATED_DATA_NASC)
            .altura(UPDATED_ALTURA)
            .morada(UPDATED_MORADA)
            .codPost(UPDATED_COD_POST)
            .freguesia(UPDATED_FREGUESIA)
            .nif(UPDATED_NIF)
            .medFam(UPDATED_MED_FAM)
            .sexo(UPDATED_SEXO)
            .telef(UPDATED_TELEF)
            .telef2(UPDATED_TELEF_2)
            .docId(UPDATED_DOC_ID)
            .nBenef(UPDATED_N_BENEF)
            .nUtente(UPDATED_N_UTENTE)
            .numProcHosp(UPDATED_NUM_PROC_HOSP);

        restDoenteIdentidadeMockMvc.perform(put("/api/doente-identidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenteIdentidade)))
            .andExpect(status().isOk());

        // Validate the DoenteIdentidade in the database
        List<DoenteIdentidade> doenteIdentidadeList = doenteIdentidadeRepository.findAll();
        assertThat(doenteIdentidadeList).hasSize(databaseSizeBeforeUpdate);
        DoenteIdentidade testDoenteIdentidade = doenteIdentidadeList.get(doenteIdentidadeList.size() - 1);
        assertThat(testDoenteIdentidade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDoenteIdentidade.getDataNasc()).isEqualTo(UPDATED_DATA_NASC);
        assertThat(testDoenteIdentidade.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDoenteIdentidade.getMorada()).isEqualTo(UPDATED_MORADA);
        assertThat(testDoenteIdentidade.getCodPost()).isEqualTo(UPDATED_COD_POST);
        assertThat(testDoenteIdentidade.getFreguesia()).isEqualTo(UPDATED_FREGUESIA);
        assertThat(testDoenteIdentidade.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDoenteIdentidade.getMedFam()).isEqualTo(UPDATED_MED_FAM);
        assertThat(testDoenteIdentidade.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDoenteIdentidade.getTelef()).isEqualTo(UPDATED_TELEF);
        assertThat(testDoenteIdentidade.getTelef2()).isEqualTo(UPDATED_TELEF_2);
        assertThat(testDoenteIdentidade.getDocId()).isEqualTo(UPDATED_DOC_ID);
        assertThat(testDoenteIdentidade.getnBenef()).isEqualTo(UPDATED_N_BENEF);
        assertThat(testDoenteIdentidade.getnUtente()).isEqualTo(UPDATED_N_UTENTE);
        assertThat(testDoenteIdentidade.getNumProcHosp()).isEqualTo(UPDATED_NUM_PROC_HOSP);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenteIdentidade() throws Exception {
        int databaseSizeBeforeUpdate = doenteIdentidadeRepository.findAll().size();

        // Create the DoenteIdentidade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoenteIdentidadeMockMvc.perform(put("/api/doente-identidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenteIdentidade)))
            .andExpect(status().isBadRequest());

        // Validate the DoenteIdentidade in the database
        List<DoenteIdentidade> doenteIdentidadeList = doenteIdentidadeRepository.findAll();
        assertThat(doenteIdentidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenteIdentidade() throws Exception {
        // Initialize the database
        doenteIdentidadeRepository.saveAndFlush(doenteIdentidade);

        int databaseSizeBeforeDelete = doenteIdentidadeRepository.findAll().size();

        // Delete the doenteIdentidade
        restDoenteIdentidadeMockMvc.perform(delete("/api/doente-identidades/{id}", doenteIdentidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoenteIdentidade> doenteIdentidadeList = doenteIdentidadeRepository.findAll();
        assertThat(doenteIdentidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
