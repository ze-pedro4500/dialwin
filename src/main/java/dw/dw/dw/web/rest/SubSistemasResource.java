package dw.dw.dw.web.rest;

import dw.dw.dw.domain.SubSistemas;
import dw.dw.dw.repository.SubSistemasRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.SubSistemas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubSistemasResource {

    private final Logger log = LoggerFactory.getLogger(SubSistemasResource.class);

    private static final String ENTITY_NAME = "subSistemas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubSistemasRepository subSistemasRepository;

    public SubSistemasResource(SubSistemasRepository subSistemasRepository) {
        this.subSistemasRepository = subSistemasRepository;
    }

    /**
     * {@code POST  /sub-sistemas} : Create a new subSistemas.
     *
     * @param subSistemas the subSistemas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subSistemas, or with status {@code 400 (Bad Request)} if the subSistemas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-sistemas")
    public ResponseEntity<SubSistemas> createSubSistemas(@RequestBody SubSistemas subSistemas) throws URISyntaxException {
        log.debug("REST request to save SubSistemas : {}", subSistemas);
        if (subSistemas.getId() != null) {
            throw new BadRequestAlertException("A new subSistemas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubSistemas result = subSistemasRepository.save(subSistemas);
        return ResponseEntity.created(new URI("/api/sub-sistemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-sistemas} : Updates an existing subSistemas.
     *
     * @param subSistemas the subSistemas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subSistemas,
     * or with status {@code 400 (Bad Request)} if the subSistemas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subSistemas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-sistemas")
    public ResponseEntity<SubSistemas> updateSubSistemas(@RequestBody SubSistemas subSistemas) throws URISyntaxException {
        log.debug("REST request to update SubSistemas : {}", subSistemas);
        if (subSistemas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubSistemas result = subSistemasRepository.save(subSistemas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subSistemas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-sistemas} : get all the subSistemas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subSistemas in body.
     */
    @GetMapping("/sub-sistemas")
    public List<SubSistemas> getAllSubSistemas() {
        log.debug("REST request to get all SubSistemas");
        return subSistemasRepository.findAll();
    }

    /**
     * {@code GET  /sub-sistemas/:id} : get the "id" subSistemas.
     *
     * @param id the id of the subSistemas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subSistemas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-sistemas/{id}")
    public ResponseEntity<SubSistemas> getSubSistemas(@PathVariable Long id) {
        log.debug("REST request to get SubSistemas : {}", id);
        Optional<SubSistemas> subSistemas = subSistemasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subSistemas);
    }

    /**
     * {@code DELETE  /sub-sistemas/:id} : delete the "id" subSistemas.
     *
     * @param id the id of the subSistemas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-sistemas/{id}")
    public ResponseEntity<Void> deleteSubSistemas(@PathVariable Long id) {
        log.debug("REST request to delete SubSistemas : {}", id);
        subSistemasRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
