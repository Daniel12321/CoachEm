package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TraineeSkill")
@Getter
@Setter
@NoArgsConstructor
public class TraineeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String progress;
    private String report;


    @OneToMany(mappedBy = "traineeSkill")
    private List<Feedback> feedbacks = new ArrayList<Feedback>();



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="skill_id")
    private Skill skill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="person_id")
    Person person;


}
