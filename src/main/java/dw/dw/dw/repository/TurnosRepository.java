package dw.dw.dw.repository;
import dw.dw.dw.domain.Turnos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Turnos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnosRepository extends JpaRepository<Turnos, Long> {

}
