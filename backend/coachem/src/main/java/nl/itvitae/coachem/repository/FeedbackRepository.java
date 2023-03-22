package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FeedbackRepository extends CrudRepository<Feedback,Long> {

    List<Feedback> findByTraineeSkillId(Long id);

    @Query("SELECT f FROM Feedback f WHERE f.traineeSkill.user.id = ?1 AND f.notified = 0")
    List<Feedback> getAllUnseen(Long personId);
}
