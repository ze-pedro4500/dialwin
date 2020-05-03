package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteContactos;
import dw.dw.dw.repository.DoenteContactosRepository;
import dw.dw.dw.service.DoenteContactosService;
import dw.dw.dw.service.DoenteService;
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
 * REST controller for managing {@link dw.dw.dw.domain.DoenteContactos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteContactosResource {

    private final Logger log = LoggerFactory.getLogger(DoenteContactosResource.class);

    private static final String ENTITY_NAME = "doenteContactos";

    @Autowired
    private DoenteContactosService doenteContactosService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteContactosRepository doenteContactosRepository;

    public DoenteContactosResource(DoenteContactosRepository doenteContactosRepository) {
        this.doenteContactosRepository = doenteContactosRepository;
    }

    /**
     * {@code POST  /doente-contactos} : Create a new doenteContactos.
     *
     * @param doenteContactos the doenteContactos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteContactos, or with status {@code 400 (Bad Request)} if the doenteContactos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-contactos")
    public ResponseEntity<DoenteContactos> createDoenteContactos(@RequestBody DoenteContactos doenteContactos) throws URISyntaxException {
        log.debug("REST request to save DoenteContactos : {}", doenteContactos);
        if (doenteContactos.getId() != null) {
            throw new BadRequestAlertException("A new doenteContactos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteContactos result = doenteContactosRepository.save(doenteContactos);
        return ResponseEntity.created(new URI("/api/doente-contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-contactos} : Updates an existing doenteContactos.
     *
     * @param doenteContactos the doenteContactos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteContactos,
     * or with status {@code 400 (Bad Request)} if the doenteContactos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteContactos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-contactos")
    public ResponseEntity<DoenteContactos> updateDoenteContactos(@RequestBody DoenteContactos doenteContactos) throws URISyntaxException {
        log.debug("REST request to update DoenteContactos : {}", doenteContactos);
        if (doenteContactos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteContactos result = doenteContactosRepository.save(doenteContactos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteContactos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doente-contactos} : get all the doenteContactos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doenteContactos in body.
     */
    @GetMapping("/doente-contactos")
    public List<DoenteContactos> getAllDoenteContactos(@RequestParam(required = false, name = "doente") Long doente) {
        if(doente==null){
            return doenteContactosRepository.findAll();
        }
        List<DoenteContactos> doenteContactos = doenteContactosService.getDoenteContactos(doente);
        return doenteContactos;
    }


    /**
     * {@code GET  /doente-contactos/:id} : get the "id" doenteContactos.
     *
     * @param id the id of the doenteContactos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenteContactos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doente-contactos/{id}")
    public ResponseEntity<DoenteContactos> getDoenteContactos(@PathVariable Long id) {
        log.debug("REST request to get DoenteContactos : {}", id);
        Optional<DoenteContactos> doenteContactos = doenteContactosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteContactos);
    }

    /**
     * {@code DELETE  /doente-contactos/:id} : delete the "id" doenteContactos.
     *
     * @param id the id of the doenteContactos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-contactos/{id}")
    public ResponseEntity<Void> deleteDoenteContactos(@PathVariable Long id) {
        log.debug("REST request to delete DoenteContactos : {}", id);
        doenteContactosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
