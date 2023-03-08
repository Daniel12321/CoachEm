package nl.itvitae.coachem.dto;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import nl.itvitae.coachem.models.TraineeSkill;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SkillPostDto {

    private String name;
    private Boolean type;
    private List<TraineeSkill> TraineeSkills;

}
