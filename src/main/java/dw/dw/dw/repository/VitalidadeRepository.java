package dw.dw.dw.repository;
import dw.dw.dw.domain.Vitalidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vitalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VitalidadeRepository extends JpaRepository<Vitalidade, Long> {

}
