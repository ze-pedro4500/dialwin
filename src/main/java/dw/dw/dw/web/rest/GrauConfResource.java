package dw.dw.dw.web.rest;

import dw.dw.dw.domain.GrauConf;
import dw.dw.dw.repository.GrauConfRepository;
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
 * REST controller for managing {@link dw.dw.dw.domain.GrauConf}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrauConfResource {

    private final Logger log = LoggerFactory.getLogger(GrauConfResource.class);

    private static final String ENTITY_NAME = "grauConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrauConfRepository grauConfRepository;

    public GrauConfResource(GrauConfRepository grauConfRepository) {
        this.grauConfRepository = grauConfRepository;
    }

    /**
     * {@code POST  /grau-confs} : Create a new grauConf.
     *
     * @param grauConf the grauConf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grauConf, or with status {@code 400 (Bad Request)} if the grauConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grau-confs")
    public ResponseEntity<GrauConf> createGrauConf(@RequestBody GrauConf grauConf) throws URISyntaxException {
        log.debug("REST request to save GrauConf : {}", grauConf);
        if (grauConf.getId() != null) {
            throw new BadRequestAlertException("A new grauConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrauConf result = grauConfRepository.save(grauConf);
        return ResponseEntity.created(new URI("/api/grau-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grau-confs} : Updates an existing grauConf.
     *
     * @param grauConf the grauConf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grauConf,
     * or with status {@code 400 (Bad Request)} if the grauConf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grauConf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grau-confs")
    public ResponseEntity<GrauConf> updateGrauConf(@RequestBody GrauConf grauConf) throws URISyntaxException {
        log.debug("REST request to update GrauConf : {}", grauConf);
        if (grauConf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrauConf result = grauConfRepository.save(grauConf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grauConf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grau-confs} : get all the grauConfs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grauConfs in body.
     */
    @GetMapping("/grau-confs")
    public List<GrauConf> getAllGrauConfs() {
        log.debug("REST request to get all GrauConfs");
        return grauConfRepository.findAll();
    }

    /**
     * {@code GET  /grau-confs/:id} : get the "id" grauConf.
     *
     * @param id the id of the grauConf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grauConf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grau-confs/{id}")
    public ResponseEntity<GrauConf> getGrauConf(@PathVariable Long id) {
        log.debug("REST request to get GrauConf : {}", id);
        Optional<GrauConf> grauConf = grauConfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grauConf);
    }

    /**
     * {@code DELETE  /grau-confs/:id} : delete the "id" grauConf.
     *
     * @param id the id of the grauConf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grau-confs/{id}")
    public ResponseEntity<Void> deleteGrauConf(@PathVariable Long id) {
        log.debug("REST request to delete GrauConf : {}", id);
        grauConfRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
