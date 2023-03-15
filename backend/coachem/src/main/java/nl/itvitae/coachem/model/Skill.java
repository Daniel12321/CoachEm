package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

@Entity
@Table(name = "Skill")
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


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    //@OneToMany(mappedBy = "skill")
    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL)
    private List<TraineeSkill> traineeSkills = new ArrayList<TraineeSkill>();
}
