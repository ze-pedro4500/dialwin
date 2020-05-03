package dw.dw.dw.repository;
import dw.dw.dw.domain.SubSistemas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubSistemas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubSistemasRepository extends JpaRepository<SubSistemas, Long> {

}
