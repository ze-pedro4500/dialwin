package dw.dw.dw.repository;
import dw.dw.dw.domain.DoenteContactosOutros;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DoenteContactosOutros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteContactosOutrosRepository extends JpaRepository<DoenteContactosOutros, Long> {
    List<DoenteContactosOutros> findAllByDoenteId(Long doente);

}
