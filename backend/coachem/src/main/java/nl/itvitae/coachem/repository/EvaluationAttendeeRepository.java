package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.EvaluationAttendee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationAttendeeRepository extends CrudRepository<EvaluationAttendee, Long> {

    @Query("SELECT a FROM EvaluationAttendee a WHERE a.person.id = ?1 AND a.notified = 0")
    List<EvaluationAttendee> getAllUnseen(Long personId);

    boolean existsByEvaluationIdAndPersonId(Long evaluationId, Long personId);
    void deleteByEvaluationIdAndPersonId(Long evaluationId, Long personId);

    List<EvaluationAttendee> findByPersonId(Long personId);
}