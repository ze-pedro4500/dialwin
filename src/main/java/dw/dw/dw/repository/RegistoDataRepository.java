package dw.dw.dw.repository;

import dw.dw.dw.domain.RegistoData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RegistoData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistoDataRepository extends JpaRepository<RegistoData, Long> {
    List<RegistoData> findByRegisto(Long registo);
}
