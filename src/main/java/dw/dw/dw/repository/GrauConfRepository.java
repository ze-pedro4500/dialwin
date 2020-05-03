package dw.dw.dw.repository;
import dw.dw.dw.domain.GrauConf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GrauConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrauConfRepository extends JpaRepository<GrauConf, Long> {

}
