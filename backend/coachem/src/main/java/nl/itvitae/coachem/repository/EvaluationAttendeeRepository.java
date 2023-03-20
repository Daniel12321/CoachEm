package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.EvaluationAttendee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationAttendeeRepository extends CrudRepository<EvaluationAttendee, Long> {

    boolean existsByEvaluationIdAndPersonId(Long evaluationId, Long personId);
    void deleteByEvaluationIdAndPersonId(Long evaluationId, Long personId);

    List<EvaluationAttendee> findByPersonId(Long personId);
}
