package dw.dw.dw.repository;
import dw.dw.dw.domain.CentroSaude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CentroSaude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentroSaudeRepository extends JpaRepository<CentroSaude, Long> {

}
