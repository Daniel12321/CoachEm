package nl.itvitae.coachem.dto;

public record UserDto(String email, String role) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper {
    }
}
