package dw.dw.dw.repository;
import dw.dw.dw.domain.DoenteDiagnosticoSocial;
import dw.dw.dw.domain.DoenteRegistosIntervencoes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DoenteRegistosIntervencoes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteRegistosIntervencoesRepository extends JpaRepository<DoenteRegistosIntervencoes, Long> {
    List<DoenteRegistosIntervencoes> findAllByDoenteId(Long doente);
}
