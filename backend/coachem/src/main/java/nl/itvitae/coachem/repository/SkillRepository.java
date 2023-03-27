package nl.itvitae.coachem.repository;

import nl.itvitae.coachem.model.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface SkillRepository extends CrudRepository <Skill, Long> {
}
