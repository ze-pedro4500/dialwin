package dw.dw.dw.repository;
import dw.dw.dw.domain.DoenteContactos;
import dw.dw.dw.domain.DoenteDiagnosticoSocial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DoenteDiagnosticoSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteDiagnosticoSocialRepository extends JpaRepository<DoenteDiagnosticoSocial, Long> {

    List<DoenteDiagnosticoSocial> findAllByDoenteId(Long doente);
}
