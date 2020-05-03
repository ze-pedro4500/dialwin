package dw.dw.dw.repository;
import dw.dw.dw.domain.SitProf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SitProf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SitProfRepository extends JpaRepository<SitProf, Long> {

}
