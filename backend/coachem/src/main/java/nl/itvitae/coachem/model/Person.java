package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "person_id")
    private InfoChange infoChange;
}
