package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.model.TraineeSkill;

public record TraineeSkillDTO(Long id, String progress, String report,  Skill skill) {
    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<TraineeSkill, TraineeSkillDTO> {    }
}


