package nl.itvitae.coachem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private InfoChange infoChange;

    @JsonIgnore
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<EvaluationAttendee> evaluations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Invite> sentInvites = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "invited", cascade = CascadeType.ALL)
    private List<Invite> receivedInvites = new ArrayList<>();

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
