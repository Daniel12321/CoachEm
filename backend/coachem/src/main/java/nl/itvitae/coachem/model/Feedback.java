package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private String time;
    private Boolean notified;

    @ManyToOne
    @JoinColumn(name="trainee_skill_id")
    private TraineeSkill traineeSkill;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Person person;

    public Feedback(String text, String time, TraineeSkill traineeSkill, Person person) {
        this.text = text;
        this.time = time;
        this.traineeSkill = traineeSkill;
        this.person = person;
        this.notified= false;
    }
}
