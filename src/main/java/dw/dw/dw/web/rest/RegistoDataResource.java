package dw.dw.dw.web.rest;

import dw.dw.dw.domain.RegistoData;
import dw.dw.dw.service.RegistoDataService;
import dw.dw.dw.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link dw.dw.dw.domain.RegistoData}.
 */
@RestController
@RequestMapping("/api")
public class RegistoDataResource {

    private final Logger log = LoggerFactory.getLogger(RegistoDataResource.class);

    private static final String ENTITY_NAME = "registoData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistoDataService registoDataService;
    public RegistoDataResource(RegistoDataService registoDataService) {
        this.registoDataService = registoDataService;
    }

    /**
     * {@code POST  /registo-data} : Create a new registoData.
     *
     * @param registoData the registoData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registoData, or with status {@code 400 (Bad Request)} if the registoData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registo-data")
    public ResponseEntity<RegistoData> createRegistoData(@RequestBody RegistoData registoData) throws URISyntaxException {
        log.debug("REST request to save RegistoData : {}", registoData);
        if (registoData.getId() != null) {
            throw new BadRequestAlertException("A new registoData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistoData result = registoDataService.save(registoData);
        return ResponseEntity.created(new URI("/api/registo-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registo-data} : Updates an existing registoData.
     *
     * @param registoData the registoData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registoData,
     * or with status {@code 400 (Bad Request)} if the registoData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registoData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registo-data")
    public ResponseEntity<RegistoData> updateRegistoData(@RequestBody RegistoData registoData) throws URISyntaxException {
        log.debug("REST request to update RegistoData : {}", registoData);
        if (registoData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegistoData result = registoDataService.save(registoData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registoData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registo-data} : get all the registoData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registoData in body.
     */
    @GetMapping("/registo-data")
    public List<RegistoData> getAllRegistoData(@RequestParam(required = false, name = "registo") Long registo) {
        return registoDataService.findByRegisto(registo);
    }

    /**
     * {@code GET  /registo-data/:id} : get the "id" registoData.
     *
     * @param id the id of the registoData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registoData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registo-data/{id}")
    public ResponseEntity<RegistoData> getRegistoData(@PathVariable Long id) {
        log.debug("REST request to get RegistoData : {}", id);
        Optional<RegistoData> registoData = registoDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registoData);
    }

    /**
     * {@code DELETE  /registo-data/:id} : delete the "id" registoData.
     *
     * @param id the id of the registoData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registo-data/{id}")
    public ResponseEntity<Void> deleteRegistoData(@PathVariable Long id) {
        log.debug("REST request to delete RegistoData : {}", id);
        registoDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
