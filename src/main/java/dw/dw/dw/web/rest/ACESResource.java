package dw.dw.dw.web.rest;

import dw.dw.dw.domain.ACES;
import dw.dw.dw.repository.ACESRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.ACES}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ACESResource {

    private final Logger log = LoggerFactory.getLogger(ACESResource.class);

    private static final String ENTITY_NAME = "aCES";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ACESRepository aCESRepository;

    public ACESResource(ACESRepository aCESRepository) {
        this.aCESRepository = aCESRepository;
    }

    /**
     * {@code POST  /aces} : Create a new aCES.
     *
     * @param aCES the aCES to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aCES, or with status {@code 400 (Bad Request)} if the aCES has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aces")
    public ResponseEntity<ACES> createACES(@RequestBody ACES aCES) throws URISyntaxException {
        log.debug("REST request to save ACES : {}", aCES);
        if (aCES.getId() != null) {
            throw new BadRequestAlertException("A new aCES cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ACES result = aCESRepository.save(aCES);
        return ResponseEntity.created(new URI("/api/aces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aces} : Updates an existing aCES.
     *
     * @param aCES the aCES to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aCES,
     * or with status {@code 400 (Bad Request)} if the aCES is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aCES couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aces")
    public ResponseEntity<ACES> updateACES(@RequestBody ACES aCES) throws URISyntaxException {
        log.debug("REST request to update ACES : {}", aCES);
        if (aCES.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ACES result = aCESRepository.save(aCES);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aCES.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aces} : get all the aCES.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aCES in body.
     */
    @GetMapping("/aces")
    public List<ACES> getAllACES() {
        log.debug("REST request to get all ACES");
        return aCESRepository.findAll();
    }

    /**
     * {@code GET  /aces/:id} : get the "id" aCES.
     *
     * @param id the id of the aCES to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aCES, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aces/{id}")
    public ResponseEntity<ACES> getACES(@PathVariable Long id) {
        log.debug("REST request to get ACES : {}", id);
        Optional<ACES> aCES = aCESRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aCES);
    }

    /**
     * {@code DELETE  /aces/:id} : delete the "id" aCES.
     *
     * @param id the id of the aCES to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aces/{id}")
    public ResponseEntity<Void> deleteACES(@PathVariable Long id) {
        log.debug("REST request to delete ACES : {}", id);
        aCESRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
