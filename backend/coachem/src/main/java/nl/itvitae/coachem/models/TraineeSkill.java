package nl.itvitae.coachem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Trainee-Skill")
@Getter
@Setter
public class TraineeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String progress;
    private String report;
    // Person id

    @ManyToOne(cascade = CascadeType.ALL)
    private Skill skill;

}
