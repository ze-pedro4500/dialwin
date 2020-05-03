package dw.dw.dw.repository;
import dw.dw.dw.domain.ACES;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ACES entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ACESRepository extends JpaRepository<ACES, Long> {

}
