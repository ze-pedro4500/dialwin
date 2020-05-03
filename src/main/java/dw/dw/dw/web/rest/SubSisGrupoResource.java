package dw.dw.dw.web.rest;

import dw.dw.dw.domain.SubSisGrupo;
import dw.dw.dw.repository.SubSisGrupoRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.SubSisGrupo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubSisGrupoResource {

    private final Logger log = LoggerFactory.getLogger(SubSisGrupoResource.class);

    private static final String ENTITY_NAME = "subSisGrupo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubSisGrupoRepository subSisGrupoRepository;

    public SubSisGrupoResource(SubSisGrupoRepository subSisGrupoRepository) {
        this.subSisGrupoRepository = subSisGrupoRepository;
    }

    /**
     * {@code POST  /sub-sis-grupos} : Create a new subSisGrupo.
     *
     * @param subSisGrupo the subSisGrupo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subSisGrupo, or with status {@code 400 (Bad Request)} if the subSisGrupo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-sis-grupos")
    public ResponseEntity<SubSisGrupo> createSubSisGrupo(@RequestBody SubSisGrupo subSisGrupo) throws URISyntaxException {
        log.debug("REST request to save SubSisGrupo : {}", subSisGrupo);
        if (subSisGrupo.getId() != null) {
            throw new BadRequestAlertException("A new subSisGrupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubSisGrupo result = subSisGrupoRepository.save(subSisGrupo);
        return ResponseEntity.created(new URI("/api/sub-sis-grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-sis-grupos} : Updates an existing subSisGrupo.
     *
     * @param subSisGrupo the subSisGrupo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subSisGrupo,
     * or with status {@code 400 (Bad Request)} if the subSisGrupo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subSisGrupo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-sis-grupos")
    public ResponseEntity<SubSisGrupo> updateSubSisGrupo(@RequestBody SubSisGrupo subSisGrupo) throws URISyntaxException {
        log.debug("REST request to update SubSisGrupo : {}", subSisGrupo);
        if (subSisGrupo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubSisGrupo result = subSisGrupoRepository.save(subSisGrupo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subSisGrupo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-sis-grupos} : get all the subSisGrupos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subSisGrupos in body.
     */
    @GetMapping("/sub-sis-grupos")
    public List<SubSisGrupo> getAllSubSisGrupos() {
        log.debug("REST request to get all SubSisGrupos");
        return subSisGrupoRepository.findAll();
    }

    /**
     * {@code GET  /sub-sis-grupos/:id} : get the "id" subSisGrupo.
     *
     * @param id the id of the subSisGrupo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subSisGrupo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-sis-grupos/{id}")
    public ResponseEntity<SubSisGrupo> getSubSisGrupo(@PathVariable Long id) {
        log.debug("REST request to get SubSisGrupo : {}", id);
        Optional<SubSisGrupo> subSisGrupo = subSisGrupoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subSisGrupo);
    }

    /**
     * {@code DELETE  /sub-sis-grupos/:id} : delete the "id" subSisGrupo.
     *
     * @param id the id of the subSisGrupo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-sis-grupos/{id}")
    public ResponseEntity<Void> deleteSubSisGrupo(@PathVariable Long id) {
        log.debug("REST request to delete SubSisGrupo : {}", id);
        subSisGrupoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
