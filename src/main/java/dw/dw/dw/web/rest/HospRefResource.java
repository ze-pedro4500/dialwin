package dw.dw.dw.web.rest;

import dw.dw.dw.domain.HospRef;
import dw.dw.dw.repository.HospRefRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.HospRef}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HospRefResource {

    private final Logger log = LoggerFactory.getLogger(HospRefResource.class);

    private static final String ENTITY_NAME = "hospRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospRefRepository hospRefRepository;

    public HospRefResource(HospRefRepository hospRefRepository) {
        this.hospRefRepository = hospRefRepository;
    }

    /**
     * {@code POST  /hosp-refs} : Create a new hospRef.
     *
     * @param hospRef the hospRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospRef, or with status {@code 400 (Bad Request)} if the hospRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hosp-refs")
    public ResponseEntity<HospRef> createHospRef(@RequestBody HospRef hospRef) throws URISyntaxException {
        log.debug("REST request to save HospRef : {}", hospRef);
        if (hospRef.getId() != null) {
            throw new BadRequestAlertException("A new hospRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HospRef result = hospRefRepository.save(hospRef);
        return ResponseEntity.created(new URI("/api/hosp-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hosp-refs} : Updates an existing hospRef.
     *
     * @param hospRef the hospRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospRef,
     * or with status {@code 400 (Bad Request)} if the hospRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hosp-refs")
    public ResponseEntity<HospRef> updateHospRef(@RequestBody HospRef hospRef) throws URISyntaxException {
        log.debug("REST request to update HospRef : {}", hospRef);
        if (hospRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HospRef result = hospRefRepository.save(hospRef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hosp-refs} : get all the hospRefs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospRefs in body.
     */
    @GetMapping("/hosp-refs")
    public List<HospRef> getAllHospRefs() {
        log.debug("REST request to get all HospRefs");
        return hospRefRepository.findAll();
    }

    /**
     * {@code GET  /hosp-refs/:id} : get the "id" hospRef.
     *
     * @param id the id of the hospRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hosp-refs/{id}")
    public ResponseEntity<HospRef> getHospRef(@PathVariable Long id) {
        log.debug("REST request to get HospRef : {}", id);
        Optional<HospRef> hospRef = hospRefRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hospRef);
    }

    /**
     * {@code DELETE  /hosp-refs/:id} : delete the "id" hospRef.
     *
     * @param id the id of the hospRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hosp-refs/{id}")
    public ResponseEntity<Void> deleteHospRef(@PathVariable Long id) {
        log.debug("REST request to delete HospRef : {}", id);
        hospRefRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
