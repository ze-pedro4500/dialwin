package dw.dw.dw.repository;
import dw.dw.dw.domain.Habit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Habit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

}
