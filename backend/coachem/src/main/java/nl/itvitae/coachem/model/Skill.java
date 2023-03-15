package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean type;
    private String description;
    private String time;
    private Integer duration;
    private String category;
    @JsonIgnore
    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL)
    private List<TraineeSkill> traineeSkills = new ArrayList<TraineeSkill>();
}
