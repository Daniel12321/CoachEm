package nl.itvitae.coachem.model;

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
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String time;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Person trainee;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "evaluationattendee",
            joinColumns = @JoinColumn(name = "evaluation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(name = "PK_evaluationattendee", columnNames = {"evaluation_id", "user_id"})
    )
    private List<Person> attendees = new ArrayList<>();

    public Evaluation(String time, Person trainee, List<Person> attendees) {
        this.time = time;
        this.trainee = trainee;
        this.attendees = attendees;
    }
}
