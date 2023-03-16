package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FeedbackRepository extends CrudRepository<Feedback,Long> {
    List<Feedback> findByTraineeSkillId(Long id);
}
