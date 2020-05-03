package dw.dw.dw.web.rest;

import dw.dw.dw.domain.HorarioDoente;
import dw.dw.dw.repository.HorarioDoenteRepository;
import dw.dw.dw.service.DoenteHorarioService;
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
 * REST controller for managing {@link dw.dw.dw.domain.HorarioDoente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HorarioDoenteResource {

    private final Logger log = LoggerFactory.getLogger(HorarioDoenteResource.class);

    private static final String ENTITY_NAME = "horarioDoente";

    @Autowired
    private DoenteHorarioService doenteHorarioService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorarioDoenteRepository horarioDoenteRepository;

    public HorarioDoenteResource(HorarioDoenteRepository horarioDoenteRepository) {
        this.horarioDoenteRepository = horarioDoenteRepository;
    }

    /**
     * {@code POST  /horario-doentes} : Create a new horarioDoente.
     *
     * @param horarioDoente the horarioDoente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horarioDoente, or with status {@code 400 (Bad Request)} if the horarioDoente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horario-doentes")
    public ResponseEntity<HorarioDoente> createHorarioDoente(@RequestBody HorarioDoente horarioDoente) throws URISyntaxException {
        log.debug("REST request to save HorarioDoente : {}", horarioDoente);
        if (horarioDoente.getId() != null) {
            throw new BadRequestAlertException("A new horarioDoente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioDoente result = horarioDoenteRepository.save(horarioDoente);
        return ResponseEntity.created(new URI("/api/horario-doentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horario-doentes} : Updates an existing horarioDoente.
     *
     * @param horarioDoente the horarioDoente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horarioDoente,
     * or with status {@code 400 (Bad Request)} if the horarioDoente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horarioDoente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horario-doentes")
    public ResponseEntity<HorarioDoente> updateHorarioDoente(@RequestBody HorarioDoente horarioDoente) throws URISyntaxException {
        log.debug("REST request to update HorarioDoente : {}", horarioDoente);
        if (horarioDoente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioDoente result = horarioDoenteRepository.save(horarioDoente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, horarioDoente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horario-doentes} : get all the horarioDoentes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horarioDoentes in body.
     */
    @GetMapping("/horario-doentes")
    public List<HorarioDoente> getAllHorarioDoentes(@RequestParam(required = false, name = "doente") Long doente) {
        return doenteHorarioService.getDoenteHor(doente);
    }

    /**
     * {@code GET  /horario-doentes/:id} : get the "id" horarioDoente.
     *
     * @param id the id of the horarioDoente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horarioDoente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horario-doentes/{id}")
    public ResponseEntity<HorarioDoente> getHorarioDoente(@PathVariable Long id) {
        log.debug("REST request to get HorarioDoente : {}", id);
        Optional<HorarioDoente> horarioDoente = horarioDoenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(horarioDoente);
    }

    /**
     * {@code DELETE  /horario-doentes/:id} : delete the "id" horarioDoente.
     *
     * @param id the id of the horarioDoente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horario-doentes/{id}")
    public ResponseEntity<Void> deleteHorarioDoente(@PathVariable Long id) {
        log.debug("REST request to delete HorarioDoente : {}", id);
        horarioDoenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
