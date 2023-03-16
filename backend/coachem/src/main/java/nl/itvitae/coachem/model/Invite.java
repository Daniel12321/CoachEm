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
    @JoinColumn(name = "inviter_id")
    private Person inviter;

    @ManyToOne
    @JoinColumn(name = "invited_person_id")
    private Person invitedPerson;
}
