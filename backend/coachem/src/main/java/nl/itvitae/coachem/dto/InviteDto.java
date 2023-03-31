package nl.itvitae.coachem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.model.Person;

import java.util.List;

public record InviteDto(Long id, Boolean accepted, Integer questionOne, Integer questionTwo, Integer questionThree,
                        Integer questionFour, Integer questionFive, String questionSix, PersonDto trainee, PersonDto invited) {

    @JsonIgnore
    public boolean isValid() {
        return questionOne != null &&
                questionTwo != null &&
                questionThree != null &&
                questionFour != null &&
                questionFive != null &&
                questionSix != null &&
                accepted != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Invite, InviteDto> {
    }
}
