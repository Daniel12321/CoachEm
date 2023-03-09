package nl.itvitae.coachem.dto;

import lombok.Getter;
import lombok.Setter;
import nl.itvitae.coachem.model.TraineeSkill;

import java.util.List;

@Getter
@Setter
public class SkillPostDto {

    private String name;
    private Boolean type;
    private List<TraineeSkill> TraineeSkills;

}
