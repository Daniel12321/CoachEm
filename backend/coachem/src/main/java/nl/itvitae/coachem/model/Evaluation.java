package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Person attendee;
}
