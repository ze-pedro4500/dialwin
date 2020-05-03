package dw.dw.dw.repository;
import dw.dw.dw.domain.DoenteSocioFamiliar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DoenteSocioFamiliar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteSocioFamiliarRepository extends JpaRepository<DoenteSocioFamiliar, Long> {
    DoenteSocioFamiliar findAllByDoenteId(Long doente);

}
