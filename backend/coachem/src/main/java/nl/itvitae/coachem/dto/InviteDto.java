package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.model.Person;

import java.util.List;

public record InviteDto(Long id, Boolean accepted, String time, Person inviter, Person invitedPerson) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Invite, InviteDto> {
    }
}
