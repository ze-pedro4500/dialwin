package dw.dw.dw.repository;
import dw.dw.dw.domain.DoenteIdentidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DoenteIdentidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteIdentidadeRepository extends JpaRepository<DoenteIdentidade, Long> {
    List<DoenteIdentidade> findAllByDoenteId(Long id);
    List<DoenteIdentidade> findAllBynUtente(Integer utente);
    List<DoenteIdentidade> findAllBySubsistemasId(Long sub);
}
