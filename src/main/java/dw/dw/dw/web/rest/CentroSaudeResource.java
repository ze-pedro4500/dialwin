package dw.dw.dw.web.rest;

import dw.dw.dw.domain.CentroSaude;
import dw.dw.dw.repository.CentroSaudeRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.CentroSaude}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CentroSaudeResource {

    private final Logger log = LoggerFactory.getLogger(CentroSaudeResource.class);

    private static final String ENTITY_NAME = "centroSaude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroSaudeRepository centroSaudeRepository;

    public CentroSaudeResource(CentroSaudeRepository centroSaudeRepository) {
        this.centroSaudeRepository = centroSaudeRepository;
    }

    /**
     * {@code POST  /centro-saudes} : Create a new centroSaude.
     *
     * @param centroSaude the centroSaude to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroSaude, or with status {@code 400 (Bad Request)} if the centroSaude has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centro-saudes")
    public ResponseEntity<CentroSaude> createCentroSaude(@RequestBody CentroSaude centroSaude) throws URISyntaxException {
        log.debug("REST request to save CentroSaude : {}", centroSaude);
        if (centroSaude.getId() != null) {
            throw new BadRequestAlertException("A new centroSaude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentroSaude result = centroSaudeRepository.save(centroSaude);
        return ResponseEntity.created(new URI("/api/centro-saudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centro-saudes} : Updates an existing centroSaude.
     *
     * @param centroSaude the centroSaude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroSaude,
     * or with status {@code 400 (Bad Request)} if the centroSaude is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroSaude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centro-saudes")
    public ResponseEntity<CentroSaude> updateCentroSaude(@RequestBody CentroSaude centroSaude) throws URISyntaxException {
        log.debug("REST request to update CentroSaude : {}", centroSaude);
        if (centroSaude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentroSaude result = centroSaudeRepository.save(centroSaude);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroSaude.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /centro-saudes} : get all the centroSaudes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroSaudes in body.
     */
    @GetMapping("/centro-saudes")
    public List<CentroSaude> getAllCentroSaudes() {
        log.debug("REST request to get all CentroSaudes");
        return centroSaudeRepository.findAll();
    }

    /**
     * {@code GET  /centro-saudes/:id} : get the "id" centroSaude.
     *
     * @param id the id of the centroSaude to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroSaude, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centro-saudes/{id}")
    public ResponseEntity<CentroSaude> getCentroSaude(@PathVariable Long id) {
        log.debug("REST request to get CentroSaude : {}", id);
        Optional<CentroSaude> centroSaude = centroSaudeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(centroSaude);
    }

    /**
     * {@code DELETE  /centro-saudes/:id} : delete the "id" centroSaude.
     *
     * @param id the id of the centroSaude to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centro-saudes/{id}")
    public ResponseEntity<Void> deleteCentroSaude(@PathVariable Long id) {
        log.debug("REST request to delete CentroSaude : {}", id);
        centroSaudeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
