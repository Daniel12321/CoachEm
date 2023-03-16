package nl.itvitae.coachem.dto;


import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.model.TraineeSkill;
import java.util.List;


public record SkillDTO(Long id, String name, Boolean type, String description, String time,Integer duration,String category) {
    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Skill, SkillDTO> {    }
}
