package dw.dw.dw.repository;
import dw.dw.dw.domain.SubSisGrupo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubSisGrupo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubSisGrupoRepository extends JpaRepository<SubSisGrupo, Long> {

}
