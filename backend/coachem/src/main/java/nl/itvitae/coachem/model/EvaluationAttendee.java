package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UN_EvaluationAttendee", columnNames = { "evaluation_id", "person_id" })})
@Getter
@Setter
@NoArgsConstructor
public class EvaluationAttendee {

    @Id
    @GeneratedValue
    private Long id;
    private Boolean notified;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public EvaluationAttendee(Evaluation evaluation, Person person) {
        this.notified = false;
        this.evaluation = evaluation;
        this.person = person;
    }
}
