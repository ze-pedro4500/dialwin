package dw.dw.dw.web.rest;

import dw.dw.dw.DialwinApp;
import dw.dw.dw.domain.RegistoData;
import dw.dw.dw.repository.RegistoDataRepository;
import dw.dw.dw.service.RegistoDataService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegistoDataResource} REST controller.
 */
@SpringBootTest(classes = DialwinApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RegistoDataResourceIT {

    private static final Long DEFAULT_REGISTO = 1L;
    private static final Long UPDATED_REGISTO = 2L;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RegistoDataRepository registoDataRepository;

    @Autowired
    private RegistoDataService registoDataService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistoDataMockMvc;

    private RegistoData registoData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistoData createEntity(EntityManager em) {
        RegistoData registoData = new RegistoData()
            .registo(DEFAULT_REGISTO)
            .data(DEFAULT_DATA);
        return registoData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistoData createUpdatedEntity(EntityManager em) {
        RegistoData registoData = new RegistoData()
            .registo(UPDATED_REGISTO)
            .data(UPDATED_DATA);
        return registoData;
    }

    @BeforeEach
    public void initTest() {
        registoData = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistoData() throws Exception {
        int databaseSizeBeforeCreate = registoDataRepository.findAll().size();

        // Create the RegistoData
        restRegistoDataMockMvc.perform(post("/api/registo-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registoData)))
            .andExpect(status().isCreated());

        // Validate the RegistoData in the database
        List<RegistoData> registoDataList = registoDataRepository.findAll();
        assertThat(registoDataList).hasSize(databaseSizeBeforeCreate + 1);
        RegistoData testRegistoData = registoDataList.get(registoDataList.size() - 1);
        assertThat(testRegistoData.getRegisto()).isEqualTo(DEFAULT_REGISTO);
        assertThat(testRegistoData.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createRegistoDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registoDataRepository.findAll().size();

        // Create the RegistoData with an existing ID
        registoData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistoDataMockMvc.perform(post("/api/registo-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registoData)))
            .andExpect(status().isBadRequest());

        // Validate the RegistoData in the database
        List<RegistoData> registoDataList = registoDataRepository.findAll();
        assertThat(registoDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegistoData() throws Exception {
        // Initialize the database
        registoDataRepository.saveAndFlush(registoData);

        // Get all the registoDataList
        restRegistoDataMockMvc.perform(get("/api/registo-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registoData.getId().intValue())))
            .andExpect(jsonPath("$.[*].registo").value(hasItem(DEFAULT_REGISTO.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getRegistoData() throws Exception {
        // Initialize the database
        registoDataRepository.saveAndFlush(registoData);

        // Get the registoData
        restRegistoDataMockMvc.perform(get("/api/registo-data/{id}", registoData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registoData.getId().intValue()))
            .andExpect(jsonPath("$.registo").value(DEFAULT_REGISTO.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistoData() throws Exception {
        // Get the registoData
        restRegistoDataMockMvc.perform(get("/api/registo-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistoData() throws Exception {
        // Initialize the database
        registoDataService.save(registoData);

        int databaseSizeBeforeUpdate = registoDataRepository.findAll().size();

        // Update the registoData
        RegistoData updatedRegistoData = registoDataRepository.findById(registoData.getId()).get();
        // Disconnect from session so that the updates on updatedRegistoData are not directly saved in db
        em.detach(updatedRegistoData);
        updatedRegistoData
            .registo(UPDATED_REGISTO)
            .data(UPDATED_DATA);

        restRegistoDataMockMvc.perform(put("/api/registo-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistoData)))
            .andExpect(status().isOk());

        // Validate the RegistoData in the database
        List<RegistoData> registoDataList = registoDataRepository.findAll();
        assertThat(registoDataList).hasSize(databaseSizeBeforeUpdate);
        RegistoData testRegistoData = registoDataList.get(registoDataList.size() - 1);
        assertThat(testRegistoData.getRegisto()).isEqualTo(UPDATED_REGISTO);
        assertThat(testRegistoData.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistoData() throws Exception {
        int databaseSizeBeforeUpdate = registoDataRepository.findAll().size();

        // Create the RegistoData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistoDataMockMvc.perform(put("/api/registo-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registoData)))
            .andExpect(status().isBadRequest());

        // Validate the RegistoData in the database
        List<RegistoData> registoDataList = registoDataRepository.findAll();
        assertThat(registoDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegistoData() throws Exception {
        // Initialize the database
        registoDataService.save(registoData);

        int databaseSizeBeforeDelete = registoDataRepository.findAll().size();

        // Delete the registoData
        restRegistoDataMockMvc.perform(delete("/api/registo-data/{id}", registoData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistoData> registoDataList = registoDataRepository.findAll();
        assertThat(registoDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
