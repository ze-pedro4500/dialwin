package dw.dw.dw.web.rest;

import dw.dw.dw.domain.Vitalidade;
import dw.dw.dw.repository.VitalidadeRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.Vitalidade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VitalidadeResource {

    private final Logger log = LoggerFactory.getLogger(VitalidadeResource.class);

    private static final String ENTITY_NAME = "vitalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VitalidadeRepository vitalidadeRepository;

    public VitalidadeResource(VitalidadeRepository vitalidadeRepository) {
        this.vitalidadeRepository = vitalidadeRepository;
    }

    /**
     * {@code POST  /vitalidades} : Create a new vitalidade.
     *
     * @param vitalidade the vitalidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vitalidade, or with status {@code 400 (Bad Request)} if the vitalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vitalidades")
    public ResponseEntity<Vitalidade> createVitalidade(@RequestBody Vitalidade vitalidade) throws URISyntaxException {
        log.debug("REST request to save Vitalidade : {}", vitalidade);
        if (vitalidade.getId() != null) {
            throw new BadRequestAlertException("A new vitalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vitalidade result = vitalidadeRepository.save(vitalidade);
        return ResponseEntity.created(new URI("/api/vitalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vitalidades} : Updates an existing vitalidade.
     *
     * @param vitalidade the vitalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vitalidade,
     * or with status {@code 400 (Bad Request)} if the vitalidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vitalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vitalidades")
    public ResponseEntity<Vitalidade> updateVitalidade(@RequestBody Vitalidade vitalidade) throws URISyntaxException {
        log.debug("REST request to update Vitalidade : {}", vitalidade);
        if (vitalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vitalidade result = vitalidadeRepository.save(vitalidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vitalidade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vitalidades} : get all the vitalidades.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vitalidades in body.
     */
    @GetMapping("/vitalidades")
    public List<Vitalidade> getAllVitalidades() {
        log.debug("REST request to get all Vitalidades");
        return vitalidadeRepository.findAll();
    }

    /**
     * {@code GET  /vitalidades/:id} : get the "id" vitalidade.
     *
     * @param id the id of the vitalidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vitalidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vitalidades/{id}")
    public ResponseEntity<Vitalidade> getVitalidade(@PathVariable Long id) {
        log.debug("REST request to get Vitalidade : {}", id);
        Optional<Vitalidade> vitalidade = vitalidadeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vitalidade);
    }

    /**
     * {@code DELETE  /vitalidades/:id} : delete the "id" vitalidade.
     *
     * @param id the id of the vitalidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vitalidades/{id}")
    public ResponseEntity<Void> deleteVitalidade(@PathVariable Long id) {
        log.debug("REST request to delete Vitalidade : {}", id);
        vitalidadeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
