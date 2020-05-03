package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteIdentidade;
import dw.dw.dw.domain.enumeration.Situacao;
import dw.dw.dw.repository.DoenteIdentidadeRepository;
import dw.dw.dw.service.DoenteIdentidadeService;
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
 * REST controller for managing {@link dw.dw.dw.domain.DoenteIdentidade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteIdentidadeResource {

    private final Logger log = LoggerFactory.getLogger(DoenteIdentidadeResource.class);

    private static final String ENTITY_NAME = "doenteIdentidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private DoenteIdentidadeService doenteIdentidadeService;

    private final DoenteIdentidadeRepository doenteIdentidadeRepository;

    public DoenteIdentidadeResource(DoenteIdentidadeRepository doenteIdentidadeRepository) {
        this.doenteIdentidadeRepository = doenteIdentidadeRepository;
    }

    /**
     * {@code POST  /doente-identidades} : Create a new doenteIdentidade.
     *
     * @param doenteIdentidade the doenteIdentidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteIdentidade, or with status {@code 400 (Bad Request)} if the doenteIdentidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-identidades")
    public ResponseEntity<DoenteIdentidade> createDoenteIdentidade(@RequestBody DoenteIdentidade doenteIdentidade) throws URISyntaxException {
        log.debug("REST request to save DoenteIdentidade : {}", doenteIdentidade);
        if (doenteIdentidade.getId() != null) {
            throw new BadRequestAlertException("A new doenteIdentidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteIdentidade result = doenteIdentidadeRepository.save(doenteIdentidade);
        return ResponseEntity.created(new URI("/api/doente-identidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-identidades} : Updates an existing doenteIdentidade.
     *
     * @param doenteIdentidade the doenteIdentidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteIdentidade,
     * or with status {@code 400 (Bad Request)} if the doenteIdentidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteIdentidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-identidades")
    public ResponseEntity<DoenteIdentidade> updateDoenteIdentidade(@RequestBody DoenteIdentidade doenteIdentidade) throws URISyntaxException {
        log.debug("REST request to update DoenteIdentidade : {}", doenteIdentidade);
        if (doenteIdentidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteIdentidade result = doenteIdentidadeRepository.save(doenteIdentidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteIdentidade.getId().toString()))
            .body(result);
    }


    @GetMapping("/doente-identidades")
    public List<DoenteIdentidade> getAllDoenteIdentidades() {
        return doenteIdentidadeRepository.findAll();
    }


    @GetMapping("/doente-identidade")
    public DoenteIdentidade getIdentidadeDoente(@RequestParam(required = false, name = "doente") Long doente, @RequestParam(required = false, name = "utente") Integer utente){
        DoenteIdentidade doenteIdentidade = doenteIdentidadeService.findByDoente(doente,utente);
        return doenteIdentidade;
    }


    /**
     * {@code GET  /doente-identidades/:id} : get the "id" doenteIdentidade.
     *
     * @param id the id of the doenteIdentidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenteIdentidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doente-identidades/{id}")
    public ResponseEntity<DoenteIdentidade> getDoenteIdentidade(@PathVariable Long id) {
        log.debug("REST request to get DoenteIdentidade : {}", id);
        Optional<DoenteIdentidade> doenteIdentidade = doenteIdentidadeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteIdentidade);
    }

    /**
     * {@code DELETE  /doente-identidades/:id} : delete the "id" doenteIdentidade.
     *
     * @param id the id of the doenteIdentidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-identidades/{id}")
    public ResponseEntity<Void> deleteDoenteIdentidade(@PathVariable Long id) {
        log.debug("REST request to delete DoenteIdentidade : {}", id);
        doenteIdentidadeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
