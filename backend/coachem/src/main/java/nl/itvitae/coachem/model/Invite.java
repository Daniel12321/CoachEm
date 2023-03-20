package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean accepted;
    private String time;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Person trainee;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    private Person invited;
}
