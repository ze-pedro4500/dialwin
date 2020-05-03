package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteSocioFamiliar;
import dw.dw.dw.repository.DoenteSocioFamiliarRepository;
import dw.dw.dw.service.DoenteContactosService;
import dw.dw.dw.service.DoenteSocioFamiliarService;
import dw.dw.dw.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link dw.dw.dw.domain.DoenteSocioFamiliar}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteSocioFamiliarResource {

    @Autowired
    private DoenteSocioFamiliarService doenteSocioFamiliarService;

    private final Logger log = LoggerFactory.getLogger(DoenteSocioFamiliarResource.class);

    private static final String ENTITY_NAME = "doenteSocioFamiliar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteSocioFamiliarRepository doenteSocioFamiliarRepository;

    public DoenteSocioFamiliarResource(DoenteSocioFamiliarRepository doenteSocioFamiliarRepository) {
        this.doenteSocioFamiliarRepository = doenteSocioFamiliarRepository;
    }

    /**
     * {@code POST  /doente-socio-familiars} : Create a new doenteSocioFamiliar.
     *
     * @param doenteSocioFamiliar the doenteSocioFamiliar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteSocioFamiliar, or with status {@code 400 (Bad Request)} if the doenteSocioFamiliar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-socio-familiars")
    public ResponseEntity<DoenteSocioFamiliar> createDoenteSocioFamiliar(@RequestBody DoenteSocioFamiliar doenteSocioFamiliar) throws URISyntaxException {
        log.debug("REST request to save DoenteSocioFamiliar : {}", doenteSocioFamiliar);
        if (doenteSocioFamiliar.getId() != null) {
            throw new BadRequestAlertException("A new doenteSocioFamiliar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteSocioFamiliar result = doenteSocioFamiliarRepository.save(doenteSocioFamiliar);
        return ResponseEntity.created(new URI("/api/doente-socio-familiars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-socio-familiars} : Updates an existing doenteSocioFamiliar.
     *
     * @param doenteSocioFamiliar the doenteSocioFamiliar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteSocioFamiliar,
     * or with status {@code 400 (Bad Request)} if the doenteSocioFamiliar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteSocioFamiliar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-socio-familiars")
    public ResponseEntity<DoenteSocioFamiliar> updateDoenteSocioFamiliar(@RequestBody DoenteSocioFamiliar doenteSocioFamiliar) throws URISyntaxException {
        log.debug("REST request to update DoenteSocioFamiliar : {}", doenteSocioFamiliar);
        if (doenteSocioFamiliar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteSocioFamiliar result = doenteSocioFamiliarRepository.save(doenteSocioFamiliar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteSocioFamiliar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doente-socio-familiars} : get all the doenteSocioFamiliars.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doenteSocioFamiliars in body.
     */
    @GetMapping("/doente-socio-familiars")
    public List<DoenteSocioFamiliar> getAllDoenteSocioFamiliars() {
        log.debug("REST request to get all DoenteSocioFamiliars");
        return doenteSocioFamiliarRepository.findAll();
    }

    @GetMapping("/doente-socio-familiar")
    public DoenteSocioFamiliar getSocioFamiliar(@RequestParam(required = false, name = "doente") Long doente) {
        DoenteSocioFamiliar doenteSocioFamiliar = doenteSocioFamiliarService.getbyDoente(doente);
        return doenteSocioFamiliar;
    }

    @GetMapping("/doente-socio-familiars/{id}")
    public ResponseEntity<DoenteSocioFamiliar> getDoenteSocioFamiliar(@PathVariable Long id) {
        log.debug("REST request to get DoenteSocioFamiliar : {}", id);
        Optional<DoenteSocioFamiliar> doenteSocioFamiliar = doenteSocioFamiliarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteSocioFamiliar);
    }

    /**
     * {@code DELETE  /doente-socio-familiars/:id} : delete the "id" doenteSocioFamiliar.
     *
     * @param id the id of the doenteSocioFamiliar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-socio-familiars/{id}")
    public ResponseEntity<Void> deleteDoenteSocioFamiliar(@PathVariable Long id) {
        log.debug("REST request to delete DoenteSocioFamiliar : {}", id);
        doenteSocioFamiliarRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
