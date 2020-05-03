package dw.dw.dw.web.rest;

import dw.dw.dw.domain.Doente;
import dw.dw.dw.domain.enumeration.Situacao;
import dw.dw.dw.repository.DoenteRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link dw.dw.dw.domain.Doente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteResource {

    private final Logger log = LoggerFactory.getLogger(DoenteResource.class);

    private static final String ENTITY_NAME = "doente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteRepository doenteRepository;

    @Autowired
    private DoenteService doenteService;

    public DoenteResource(DoenteRepository doenteRepository) {
        this.doenteRepository = doenteRepository;
    }

    /**
     * {@code POST  /doentes} : Create a new doente.
     *
     * @param doente the doente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doente, or with status {@code 400 (Bad Request)} if the doente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doentes")
    public ResponseEntity<Doente> createDoente(@RequestBody Doente doente) throws URISyntaxException {
        log.debug("REST request to save Doente : {}", doente);
        if (doente.getId() != null) {
            throw new BadRequestAlertException("A new doente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Doente result = doenteRepository.save(doente);
        return ResponseEntity.created(new URI("/api/doentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doentes} : Updates an existing doente.
     *
     * @param doente the doente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doente,
     * or with status {@code 400 (Bad Request)} if the doente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doentes")
    public ResponseEntity<Doente> updateDoente(@RequestBody Doente doente) throws URISyntaxException {
        log.debug("REST request to update Doente : {}", doente);
        if (doente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Doente result = doenteRepository.save(doente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doente.getId().toString()))
            .body(result);
    }

    @GetMapping("/doentes")
    public List<Doente> getAllDoentes(@RequestParam(required = false, name="situacao") Situacao situacao, @RequestParam(required = false, name = "sub") Long sub, @RequestParam(required = false, name="t") Long t) {
        return doenteService.findBySitSubTur(situacao, sub, t);
    }


    /**
     * {@code GET  /doentes/:id} : get the "id" doente.
     *
     * @param id the id of the doente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doentes/{id}")
    public ResponseEntity<Doente> getDoente(@PathVariable Long id) {
        log.debug("REST request to get Doente : {}", id);
        Optional<Doente> doente = doenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doente);
    }

    /**
     * {@code DELETE  /doentes/:id} : delete the "id" doente.
     *
     * @param id the id of the doente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doentes/{id}")
    public ResponseEntity<Void> deleteDoente(@PathVariable Long id) {
        log.debug("REST request to delete Doente : {}", id);
        doenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
