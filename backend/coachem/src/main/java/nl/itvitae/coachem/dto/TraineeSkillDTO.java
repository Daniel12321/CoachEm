package nl.itvitae.coachem.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.model.TraineeSkill;

import java.util.ArrayList;
import java.util.List;

public record TraineeSkillDTO(Long id, String progress, String report, List<Feedback> feedbacks, Skill skill, Person person) {
    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<TraineeSkill, TraineeSkillDTO> {    }
}







