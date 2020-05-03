package dw.dw.dw.repository;
import dw.dw.dw.domain.UserPermissions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserPermissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPermissionsRepository extends JpaRepository<UserPermissions, Long> {

}
