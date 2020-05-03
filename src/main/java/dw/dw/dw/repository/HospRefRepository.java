package dw.dw.dw.repository;
import dw.dw.dw.domain.HospRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HospRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospRefRepository extends JpaRepository<HospRef, Long> {

}
