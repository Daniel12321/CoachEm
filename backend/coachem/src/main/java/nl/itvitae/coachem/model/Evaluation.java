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
    private Boolean notified;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Person trainee;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<EvaluationAttendee> attendees = new ArrayList<>();

    public Evaluation(String time, Person trainee) {
        this.time = time;
        this.trainee = trainee;
    }
}
