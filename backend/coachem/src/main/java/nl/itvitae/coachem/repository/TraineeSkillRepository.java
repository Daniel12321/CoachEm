package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.TraineeSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TraineeSkillRepository extends CrudRepository<TraineeSkill,Long> {
}
