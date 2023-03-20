package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Evaluation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {

    List<Evaluation> findByTraineeId(Long traineeId);

    @Query("SELECT e FROM Evaluation e WHERE e.trainee.id = ?1 AND e.notified = 0")
    List<Evaluation> getAllUnseen(Long traineeId);
}
