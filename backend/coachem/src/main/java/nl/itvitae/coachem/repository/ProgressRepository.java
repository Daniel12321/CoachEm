package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Progress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProgressRepository extends CrudRepository<Progress, Long> {
    List<Progress> findByTraineeSkillId(Long id);
}
