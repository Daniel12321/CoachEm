package nl.itvitae.coachem.model;

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

    // TODO: 08/03/2023 check of mapped by table naam of class naam hoort te zijn 
    
    @OneToMany(mappedBy = "skill")
    private List<TraineeSkill> TraineeSkills = new ArrayList<TraineeSkill>();
}
