package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private InfoChange infoChange;

    @JsonIgnore
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Evaluation> evaluatedTrainees;

    @JsonIgnore
    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    private List<Evaluation> evaluatingAttendees;

    @JsonIgnore
    @OneToMany(mappedBy = "inviter", cascade = CascadeType.ALL)
    private List<Invite> sentInvites;

    @JsonIgnore
    @OneToMany(mappedBy = "invitedPerson", cascade = CascadeType.ALL)
    private List<Invite> receivedInvites;

    public Person(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Person(String email, String password, String name, String address, String city, String zipcode, String phonenumber, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
        this.role = role;
    }
}
