package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteContactosOutros;
import dw.dw.dw.repository.DoenteContactosOutrosRepository;
import dw.dw.dw.service.DoenteContactosService;
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
 * REST controller for managing {@link dw.dw.dw.domain.DoenteContactosOutros}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteContactosOutrosResource {

    private final Logger log = LoggerFactory.getLogger(DoenteContactosOutrosResource.class);

    private static final String ENTITY_NAME = "doenteContactosOutros";

    @Autowired
    private DoenteContactosService doenteContactosService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteContactosOutrosRepository doenteContactosOutrosRepository;

    public DoenteContactosOutrosResource(DoenteContactosOutrosRepository doenteContactosOutrosRepository) {
        this.doenteContactosOutrosRepository = doenteContactosOutrosRepository;
    }

    /**
     * {@code POST  /doente-contactos-outros} : Create a new doenteContactosOutros.
     *
     * @param doenteContactosOutros the doenteContactosOutros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteContactosOutros, or with status {@code 400 (Bad Request)} if the doenteContactosOutros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-contactos-outros")
    public ResponseEntity<DoenteContactosOutros> createDoenteContactosOutros(@RequestBody DoenteContactosOutros doenteContactosOutros) throws URISyntaxException {
        log.debug("REST request to save DoenteContactosOutros : {}", doenteContactosOutros);
        if (doenteContactosOutros.getId() != null) {
            throw new BadRequestAlertException("A new doenteContactosOutros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteContactosOutros result = doenteContactosOutrosRepository.save(doenteContactosOutros);
        return ResponseEntity.created(new URI("/api/doente-contactos-outros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-contactos-outros} : Updates an existing doenteContactosOutros.
     *
     * @param doenteContactosOutros the doenteContactosOutros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteContactosOutros,
     * or with status {@code 400 (Bad Request)} if the doenteContactosOutros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteContactosOutros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-contactos-outros")
    public ResponseEntity<DoenteContactosOutros> updateDoenteContactosOutros(@RequestBody DoenteContactosOutros doenteContactosOutros) throws URISyntaxException {
        log.debug("REST request to update DoenteContactosOutros : {}", doenteContactosOutros);
        if (doenteContactosOutros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteContactosOutros result = doenteContactosOutrosRepository.save(doenteContactosOutros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteContactosOutros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doente-contactos-outros} : get all the doenteContactosOutros.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doenteContactosOutros in body.
     */
    @GetMapping("/doente-contactos-outros")
    public List<DoenteContactosOutros> getAllDoenteContactosOutros(@RequestParam(required = false, name = "doente") Long doente) {
        if (doente == null) {
            return doenteContactosOutrosRepository.findAll();
        }
        return doenteContactosService.getDoenteContactosOutros(doente);
    }
    /**
     * {@code GET  /doente-contactos-outros/:id} : get the "id" doenteContactosOutros.
     *
     * @param id the id of the doenteContactosOutros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenteContactosOutros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doente-contactos-outros/{id}")
    public ResponseEntity<DoenteContactosOutros> getDoenteContactosOutros(@PathVariable Long id) {
        log.debug("REST request to get DoenteContactosOutros : {}", id);
        Optional<DoenteContactosOutros> doenteContactosOutros = doenteContactosOutrosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteContactosOutros);
    }

    /**
     * {@code DELETE  /doente-contactos-outros/:id} : delete the "id" doenteContactosOutros.
     *
     * @param id the id of the doenteContactosOutros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-contactos-outros/{id}")
    public ResponseEntity<Void> deleteDoenteContactosOutros(@PathVariable Long id) {
        log.debug("REST request to delete DoenteContactosOutros : {}", id);
        doenteContactosOutrosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
