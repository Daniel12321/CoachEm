package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean accepted;
    private Date time;

    @ManyToOne
    @JoinColumn(name="trainee_id")
    private Person trainee;

    @ManyToOne
    @JoinColumn(name="invited_id")
    private Person invited;
}
