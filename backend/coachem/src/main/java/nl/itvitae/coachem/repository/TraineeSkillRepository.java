package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.TraineeSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TraineeSkillRepository extends CrudRepository<TraineeSkill,Long> {

    List<TraineeSkill> findByUserId(Long user);
}
