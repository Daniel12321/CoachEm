package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Evaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {

    List<Evaluation> findByTraineeId(Long traineeId);
    List<Evaluation> findByAttendeeId(Long traineeId);
}
