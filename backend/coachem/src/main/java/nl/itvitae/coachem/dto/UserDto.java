package nl.itvitae.coachem.dto;

import org.mapstruct.Mapper;

public record UserDto(String email, String role) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper {
    }
}
