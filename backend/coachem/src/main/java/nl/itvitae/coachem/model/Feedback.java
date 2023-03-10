package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Feedback")
@Getter
@Setter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="traineeSkill_id")
    TraineeSkill traineeSkill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="person_id")
    Person person;

}
