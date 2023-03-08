package nl.itvitae.coachem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Skill")

public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean type;

    @OneToMany
    private List<TraineeSkill> TraineeSkills = new ArrayList<>();
}
