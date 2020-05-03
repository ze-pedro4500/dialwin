package dw.dw.dw.web.rest;

import dw.dw.dw.domain.UserPermissions;
import dw.dw.dw.repository.UserPermissionsRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link dw.dw.dw.domain.UserPermissions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserPermissionsResource {

    private final Logger log = LoggerFactory.getLogger(UserPermissionsResource.class);

    private static final String ENTITY_NAME = "userPermissions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPermissionsRepository userPermissionsRepository;

    public UserPermissionsResource(UserPermissionsRepository userPermissionsRepository) {
        this.userPermissionsRepository = userPermissionsRepository;
    }

    /**
     * {@code POST  /user-permissions} : Create a new userPermissions.
     *
     * @param userPermissions the userPermissions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPermissions, or with status {@code 400 (Bad Request)} if the userPermissions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-permissions")
    public ResponseEntity<UserPermissions> createUserPermissions(@RequestBody UserPermissions userPermissions) throws URISyntaxException {
        log.debug("REST request to save UserPermissions : {}", userPermissions);
        if (userPermissions.getId() != null) {
            throw new BadRequestAlertException("A new userPermissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPermissions result = userPermissionsRepository.save(userPermissions);
        return ResponseEntity.created(new URI("/api/user-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-permissions} : Updates an existing userPermissions.
     *
     * @param userPermissions the userPermissions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPermissions,
     * or with status {@code 400 (Bad Request)} if the userPermissions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPermissions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-permissions")
    public ResponseEntity<UserPermissions> updateUserPermissions(@RequestBody UserPermissions userPermissions) throws URISyntaxException {
        log.debug("REST request to update UserPermissions : {}", userPermissions);
        if (userPermissions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPermissions result = userPermissionsRepository.save(userPermissions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPermissions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-permissions} : get all the userPermissions.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPermissions in body.
     */
    @GetMapping("/user-permissions")
    public List<UserPermissions> getAllUserPermissions(@RequestParam(required = false) String filter) {
        if ("userextra-is-null".equals(filter)) {
            log.debug("REST request to get all UserPermissionss where userExtra is null");
            return StreamSupport
                .stream(userPermissionsRepository.findAll().spliterator(), false)
                .filter(userPermissions -> userPermissions.getUserExtra() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all UserPermissions");
        return userPermissionsRepository.findAll();
    }

    /**
     * {@code GET  /user-permissions/:id} : get the "id" userPermissions.
     *
     * @param id the id of the userPermissions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPermissions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-permissions/{id}")
    public ResponseEntity<UserPermissions> getUserPermissions(@PathVariable Long id) {
        log.debug("REST request to get UserPermissions : {}", id);
        Optional<UserPermissions> userPermissions = userPermissionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userPermissions);
    }

    /**
     * {@code DELETE  /user-permissions/:id} : delete the "id" userPermissions.
     *
     * @param id the id of the userPermissions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-permissions/{id}")
    public ResponseEntity<Void> deleteUserPermissions(@PathVariable Long id) {
        log.debug("REST request to delete UserPermissions : {}", id);
        userPermissionsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
