package nl.itvitae.coachem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InfoChange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String name;
    private String address;
    private String city;
    private String zipcode;
    private String phonenumber;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public InfoChange(String email, String name, String address, String city, String zipcode, String phonenumber, Person person) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
        this.person = person;
    }
}
