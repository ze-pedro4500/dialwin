package dw.dw.dw.repository;
import dw.dw.dw.domain.DoenteContactos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DoenteContactos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoenteContactosRepository extends JpaRepository<DoenteContactos, Long> {

    List<DoenteContactos> findAllByDoenteId(Long doente);
}
