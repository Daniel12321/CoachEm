package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.User;

public record UserDto(Long id, String email, String role) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<User, UserDto> {
    }
}
