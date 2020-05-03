package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteRegistosIntervencoes;
import dw.dw.dw.repository.DoenteRegistosIntervencoesRepository;
import dw.dw.dw.service.DoenteRegistosIntervencoesService;
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
 * REST controller for managing {@link dw.dw.dw.domain.DoenteRegistosIntervencoes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteRegistosIntervencoesResource {

    private final Logger log = LoggerFactory.getLogger(DoenteRegistosIntervencoesResource.class);

    @Autowired
    private DoenteRegistosIntervencoesService doenteRegistosIntervencoesService;

    private static final String ENTITY_NAME = "doenteRegistosIntervencoes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteRegistosIntervencoesRepository doenteRegistosIntervencoesRepository;

    public DoenteRegistosIntervencoesResource(DoenteRegistosIntervencoesRepository doenteRegistosIntervencoesRepository) {
        this.doenteRegistosIntervencoesRepository = doenteRegistosIntervencoesRepository;
    }

    /**
     * {@code POST  /doente-registos-intervencoes} : Create a new doenteRegistosIntervencoes.
     *
     * @param doenteRegistosIntervencoes the doenteRegistosIntervencoes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteRegistosIntervencoes, or with status {@code 400 (Bad Request)} if the doenteRegistosIntervencoes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-registos-intervencoes")
    public ResponseEntity<DoenteRegistosIntervencoes> createDoenteRegistosIntervencoes(@RequestBody DoenteRegistosIntervencoes doenteRegistosIntervencoes) throws URISyntaxException {
        log.debug("REST request to save DoenteRegistosIntervencoes : {}", doenteRegistosIntervencoes);
        if (doenteRegistosIntervencoes.getId() != null) {
            throw new BadRequestAlertException("A new doenteRegistosIntervencoes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteRegistosIntervencoes result = doenteRegistosIntervencoesRepository.save(doenteRegistosIntervencoes);
        return ResponseEntity.created(new URI("/api/doente-registos-intervencoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-registos-intervencoes} : Updates an existing doenteRegistosIntervencoes.
     *
     * @param doenteRegistosIntervencoes the doenteRegistosIntervencoes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteRegistosIntervencoes,
     * or with status {@code 400 (Bad Request)} if the doenteRegistosIntervencoes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteRegistosIntervencoes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-registos-intervencoes")
    public ResponseEntity<DoenteRegistosIntervencoes> updateDoenteRegistosIntervencoes(@RequestBody DoenteRegistosIntervencoes doenteRegistosIntervencoes) throws URISyntaxException {
        log.debug("REST request to update DoenteRegistosIntervencoes : {}", doenteRegistosIntervencoes);
        if (doenteRegistosIntervencoes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteRegistosIntervencoes result = doenteRegistosIntervencoesRepository.save(doenteRegistosIntervencoes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteRegistosIntervencoes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doente-registos-intervencoes} : get all the doenteRegistosIntervencoes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doenteRegistosIntervencoes in body.
     */
    @GetMapping("/doente-registos-intervencoes")
    public List<DoenteRegistosIntervencoes> getAllDoenteRegistosIntervencoes(@RequestParam(required = false, name = "doente") Long doente) {
        if(doente==null){
            return doenteRegistosIntervencoesRepository.findAll();
        }
        return doenteRegistosIntervencoesService.findByDoente(doente);
    }

    /**
     * {@code GET  /doente-registos-intervencoes/:id} : get the "id" doenteRegistosIntervencoes.
     *
     * @param id the id of the doenteRegistosIntervencoes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenteRegistosIntervencoes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doente-registos-intervencoes/{id}")
    public ResponseEntity<DoenteRegistosIntervencoes> getDoenteRegistosIntervencoes(@PathVariable Long id) {
        log.debug("REST request to get DoenteRegistosIntervencoes : {}", id);
        Optional<DoenteRegistosIntervencoes> doenteRegistosIntervencoes = doenteRegistosIntervencoesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteRegistosIntervencoes);
    }

    /**
     * {@code DELETE  /doente-registos-intervencoes/:id} : delete the "id" doenteRegistosIntervencoes.
     *
     * @param id the id of the doenteRegistosIntervencoes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-registos-intervencoes/{id}")
    public ResponseEntity<Void> deleteDoenteRegistosIntervencoes(@PathVariable Long id) {
        log.debug("REST request to delete DoenteRegistosIntervencoes : {}", id);
        doenteRegistosIntervencoesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
