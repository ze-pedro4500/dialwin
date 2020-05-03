package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteHistMovimentos;
import dw.dw.dw.repository.DoenteHistMovimentosRepository;
import dw.dw.dw.service.DoenteContactosService;
import dw.dw.dw.service.DoenteHistoricoMovimentosService;
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
 * REST controller for managing {@link dw.dw.dw.domain.DoenteHistMovimentos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteHistMovimentosResource {

    private final Logger log = LoggerFactory.getLogger(DoenteHistMovimentosResource.class);

    private static final String ENTITY_NAME = "doenteHistMovimentos";

    @Autowired
    private DoenteHistoricoMovimentosService doenteHistoricoMovimentosService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteHistMovimentosRepository doenteHistMovimentosRepository;

    public DoenteHistMovimentosResource(DoenteHistMovimentosRepository doenteHistMovimentosRepository) {
        this.doenteHistMovimentosRepository = doenteHistMovimentosRepository;
    }

    /**
     * {@code POST  /doente-hist-movimentos} : Create a new doenteHistMovimentos.
     *
     * @param doenteHistMovimentos the doenteHistMovimentos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteHistMovimentos, or with status {@code 400 (Bad Request)} if the doenteHistMovimentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-hist-movimentos")
    public ResponseEntity<DoenteHistMovimentos> createDoenteHistMovimentos(@RequestBody DoenteHistMovimentos doenteHistMovimentos) throws URISyntaxException {
        log.debug("REST request to save DoenteHistMovimentos : {}", doenteHistMovimentos);
        if (doenteHistMovimentos.getId() != null) {
            throw new BadRequestAlertException("A new doenteHistMovimentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteHistMovimentos result = doenteHistMovimentosRepository.save(doenteHistMovimentos);
        return ResponseEntity.created(new URI("/api/doente-hist-movimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-hist-movimentos} : Updates an existing doenteHistMovimentos.
     *
     * @param doenteHistMovimentos the doenteHistMovimentos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteHistMovimentos,
     * or with status {@code 400 (Bad Request)} if the doenteHistMovimentos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteHistMovimentos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-hist-movimentos")
    public ResponseEntity<DoenteHistMovimentos> updateDoenteHistMovimentos(@RequestBody DoenteHistMovimentos doenteHistMovimentos) throws URISyntaxException {
        log.debug("REST request to update DoenteHistMovimentos : {}", doenteHistMovimentos);
        if (doenteHistMovimentos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteHistMovimentos result = doenteHistMovimentosRepository.save(doenteHistMovimentos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteHistMovimentos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doente-hist-movimentos} : get all the doenteHistMovimentos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doenteHistMovimentos in body.
     */
    @GetMapping("/doente-hist-movimentos")
    public List<DoenteHistMovimentos> getAllDoenteHistMovimentos(@RequestParam(required = false, name = "doente") Long doente) {
        return doenteHistoricoMovimentosService.getDoenteHistMov(doente);
    }

    /**
     * {@code GET  /doente-hist-movimentos/:id} : get the "id" doenteHistMovimentos.
     *
     * @param id the id of the doenteHistMovimentos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenteHistMovimentos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doente-hist-movimentos/{id}")
    public ResponseEntity<DoenteHistMovimentos> getDoenteHistMovimentos(@PathVariable Long id) {
        log.debug("REST request to get DoenteHistMovimentos : {}", id);
        Optional<DoenteHistMovimentos> doenteHistMovimentos = doenteHistMovimentosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteHistMovimentos);
    }

    /**
     * {@code DELETE  /doente-hist-movimentos/:id} : delete the "id" doenteHistMovimentos.
     *
     * @param id the id of the doenteHistMovimentos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-hist-movimentos/{id}")
    public ResponseEntity<Void> deleteDoenteHistMovimentos(@PathVariable Long id) {
        log.debug("REST request to delete DoenteHistMovimentos : {}", id);
        doenteHistMovimentosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
