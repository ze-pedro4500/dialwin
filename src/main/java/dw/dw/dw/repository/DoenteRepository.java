package dw.dw.dw.repository;
import dw.dw.dw.domain.Doente;
import dw.dw.dw.domain.Turnos;
import dw.dw.dw.domain.enumeration.Situacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Doente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteRepository extends JpaRepository<Doente, Long> {
    List<Doente> findAllBySituacao(Situacao situacao);
    List<Doente> findAllByTurnosId(Long t);

}
