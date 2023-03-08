package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String address;
    private String city;
    private String zipcode;
    private String phonenumber;
    private String role;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private InfoChange infoChange;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Evaluation> evaluated;

    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    private List<Evaluation> evaluater;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Invite> inviter;

    @OneToMany(mappedBy = "invited", cascade = CascadeType.ALL)
    private List<Invite> invited;
}
