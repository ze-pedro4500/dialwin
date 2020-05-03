package dw.dw.dw.service;

import dw.dw.dw.domain.RegistoData;
import dw.dw.dw.repository.RegistoDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RegistoData}.
 */
@Service
@Transactional
public class RegistoDataService {

    private final Logger log = LoggerFactory.getLogger(RegistoDataService.class);

    private final RegistoDataRepository registoDataRepository;

    public RegistoDataService(RegistoDataRepository registoDataRepository) {
        this.registoDataRepository = registoDataRepository;
    }

    /**
     * Save a registoData.
     *
     * @param registoData the entity to save.
     * @return the persisted entity.
     */
    public RegistoData save(RegistoData registoData) {
        log.debug("Request to save RegistoData : {}", registoData);
        return registoDataRepository.save(registoData);
    }

    public List<RegistoData> findByRegisto(Long registo){
        return registoDataRepository.findByRegisto(registo);
    }
    /**
     * Get all the registoData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RegistoData> findAll() {
        log.debug("Request to get all RegistoData");
        return registoDataRepository.findAll();
    }

    /**
     * Get one registoData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegistoData> findOne(Long id) {
        log.debug("Request to get RegistoData : {}", id);
        return registoDataRepository.findById(id);
    }

    /**
     * Delete the registoData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RegistoData : {}", id);
        registoDataRepository.deleteById(id);
    }
}
