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

    private Integer questionOne;
    private Integer questionTwo;
    private Integer questionThree;
    private Integer questionFour;
    private Integer questionFive;
    private String questionSix;
    private String time;
    private Boolean accepted;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Person trainee;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    private Person invited;

    public Invite(Boolean accepted, Person trainee, Person invited, String time) {
        this.accepted = accepted;
        this.trainee = trainee;
        this.invited = invited;
        this.time = time;
    }
}
