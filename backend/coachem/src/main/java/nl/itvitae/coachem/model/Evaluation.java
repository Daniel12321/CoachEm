package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date time;

    @ManyToOne
    @JoinColumn(name="trainee_id")
    private Person trainee;

    @ManyToOne
    @JoinColumn(name="attendee_id")
    private Person attendee;
}
