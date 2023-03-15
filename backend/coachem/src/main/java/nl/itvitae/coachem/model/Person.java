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
    private Long id;
    private String name;
    private String address;
    private String city;
    private String zipcode;
    private String phonenumber;

    @MapsId
    @OneToOne
    private User user;

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

//    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
//    private List<TraineeSkill> traineeSkill;
    @JsonIgnore
    @OneToMany(mappedBy = "invitedPerson", cascade = CascadeType.ALL)
    private List<Invite> receivedInvites;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String address, String city, String zipcode, String phonenumber) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
    }
}
