package dw.dw.dw.web.rest;

import dw.dw.dw.domain.DoenteDiagnosticoSocial;
import dw.dw.dw.repository.DoenteDiagnosticoSocialRepository;
import dw.dw.dw.service.DoenteDiagnosticoSocialService;
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
 * REST controller for managing {@link dw.dw.dw.domain.DoenteDiagnosticoSocial}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoenteDiagnosticoSocialResource {

    private final Logger log = LoggerFactory.getLogger(DoenteDiagnosticoSocialResource.class);

    private static final String ENTITY_NAME = "doenteDiagnosticoSocial";

    @Autowired
    DoenteDiagnosticoSocialService doenteDiagnosticoSocialService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoenteDiagnosticoSocialRepository doenteDiagnosticoSocialRepository;

    public DoenteDiagnosticoSocialResource(DoenteDiagnosticoSocialRepository doenteDiagnosticoSocialRepository) {
        this.doenteDiagnosticoSocialRepository = doenteDiagnosticoSocialRepository;
    }

    /**
     * {@code POST  /doente-diagnostico-socials} : Create a new doenteDiagnosticoSocial.
     *
     * @param doenteDiagnosticoSocial the doenteDiagnosticoSocial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenteDiagnosticoSocial, or with status {@code 400 (Bad Request)} if the doenteDiagnosticoSocial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doente-diagnostico-socials")
    public ResponseEntity<DoenteDiagnosticoSocial> createDoenteDiagnosticoSocial(@RequestBody DoenteDiagnosticoSocial doenteDiagnosticoSocial) throws URISyntaxException {
        log.debug("REST request to save DoenteDiagnosticoSocial : {}", doenteDiagnosticoSocial);
        if (doenteDiagnosticoSocial.getId() != null) {
            throw new BadRequestAlertException("A new doenteDiagnosticoSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoenteDiagnosticoSocial result = doenteDiagnosticoSocialRepository.save(doenteDiagnosticoSocial);
        return ResponseEntity.created(new URI("/api/doente-diagnostico-socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doente-diagnostico-socials} : Updates an existing doenteDiagnosticoSocial.
     *
     * @param doenteDiagnosticoSocial the doenteDiagnosticoSocial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenteDiagnosticoSocial,
     * or with status {@code 400 (Bad Request)} if the doenteDiagnosticoSocial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenteDiagnosticoSocial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doente-diagnostico-socials")
    public ResponseEntity<DoenteDiagnosticoSocial> updateDoenteDiagnosticoSocial(@RequestBody DoenteDiagnosticoSocial doenteDiagnosticoSocial) throws URISyntaxException {
        log.debug("REST request to update DoenteDiagnosticoSocial : {}", doenteDiagnosticoSocial);
        if (doenteDiagnosticoSocial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoenteDiagnosticoSocial result = doenteDiagnosticoSocialRepository.save(doenteDiagnosticoSocial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doenteDiagnosticoSocial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doente-diagnostico-socials} : get all the doenteDiagnosticoSocials.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doenteDiagnosticoSocials in body.
     */
    @GetMapping("/doente-diagnostico-socials")
    public List<DoenteDiagnosticoSocial> getAllDoenteDiagnosticoSocials() {
        log.debug("REST request to get all DoenteDiagnosticoSocials");
        return doenteDiagnosticoSocialRepository.findAll();
    }

    @GetMapping("/doente-diagnostico-social")
    public DoenteDiagnosticoSocial getDoenteDiagnostic (@RequestParam(required = false, name = "doente") Long doente) {
        return doenteDiagnosticoSocialService.findByDoente(doente);
    }

    /**
     * {@code GET  /doente-diagnostico-socials/:id} : get the "id" doenteDiagnosticoSocial.
     *
     * @param id the id of the doenteDiagnosticoSocial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenteDiagnosticoSocial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doente-diagnostico-socials/{id}")
    public ResponseEntity<DoenteDiagnosticoSocial> getDoenteDiagnosticoSocial(@PathVariable Long id) {
        log.debug("REST request to get DoenteDiagnosticoSocial : {}", id);
        Optional<DoenteDiagnosticoSocial> doenteDiagnosticoSocial = doenteDiagnosticoSocialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doenteDiagnosticoSocial);
    }

    /**
     * {@code DELETE  /doente-diagnostico-socials/:id} : delete the "id" doenteDiagnosticoSocial.
     *
     * @param id the id of the doenteDiagnosticoSocial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doente-diagnostico-socials/{id}")
    public ResponseEntity<Void> deleteDoenteDiagnosticoSocial(@PathVariable Long id) {
        log.debug("REST request to delete DoenteDiagnosticoSocial : {}", id);
        doenteDiagnosticoSocialRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
