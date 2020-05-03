package dw.dw.dw.web.rest;

import dw.dw.dw.domain.SitProf;
import dw.dw.dw.repository.SitProfRepository;
import dw.dw.dw.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link dw.dw.dw.domain.SitProf}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SitProfResource {

    private final Logger log = LoggerFactory.getLogger(SitProfResource.class);

    private static final String ENTITY_NAME = "sitProf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SitProfRepository sitProfRepository;

    public SitProfResource(SitProfRepository sitProfRepository) {
        this.sitProfRepository = sitProfRepository;
    }

    /**
     * {@code POST  /sit-profs} : Create a new sitProf.
     *
     * @param sitProf the sitProf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sitProf, or with status {@code 400 (Bad Request)} if the sitProf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sit-profs")
    public ResponseEntity<SitProf> createSitProf(@RequestBody SitProf sitProf) throws URISyntaxException {
        log.debug("REST request to save SitProf : {}", sitProf);
        if (sitProf.getId() != null) {
            throw new BadRequestAlertException("A new sitProf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SitProf result = sitProfRepository.save(sitProf);
        return ResponseEntity.created(new URI("/api/sit-profs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sit-profs} : Updates an existing sitProf.
     *
     * @param sitProf the sitProf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sitProf,
     * or with status {@code 400 (Bad Request)} if the sitProf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sitProf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sit-profs")
    public ResponseEntity<SitProf> updateSitProf(@RequestBody SitProf sitProf) throws URISyntaxException {
        log.debug("REST request to update SitProf : {}", sitProf);
        if (sitProf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SitProf result = sitProfRepository.save(sitProf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sitProf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sit-profs} : get all the sitProfs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sitProfs in body.
     */
    @GetMapping("/sit-profs")
    public List<SitProf> getAllSitProfs() {
        log.debug("REST request to get all SitProfs");
        return sitProfRepository.findAll();
    }

    /**
     * {@code GET  /sit-profs/:id} : get the "id" sitProf.
     *
     * @param id the id of the sitProf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sitProf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sit-profs/{id}")
    public ResponseEntity<SitProf> getSitProf(@PathVariable Long id) {
        log.debug("REST request to get SitProf : {}", id);
        Optional<SitProf> sitProf = sitProfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sitProf);
    }

    /**
     * {@code DELETE  /sit-profs/:id} : delete the "id" sitProf.
     *
     * @param id the id of the sitProf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sit-profs/{id}")
    public ResponseEntity<Void> deleteSitProf(@PathVariable Long id) {
        log.debug("REST request to delete SitProf : {}", id);
        sitProfRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
