package nl.itvitae.coachem.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.User;

public record RegisterRequestDto(String email,
                                 String password,
                                 String role,
                                 String name,
                                 String address,
                                 String city,
                                 String zipcode,
                                 String phonenumber) {

    @JsonIgnore
    public boolean isValid() {
        return email != null && password != null && role != null && name != null && address != null && city != null && zipcode != null;
    }
}
