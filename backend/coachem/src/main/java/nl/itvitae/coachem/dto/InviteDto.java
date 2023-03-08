package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.model.Person;

public record InviteDto(Long id, Boolean accepted, String time, Person trainee, Person invited) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Invite, InviteDto> {
    }
}
