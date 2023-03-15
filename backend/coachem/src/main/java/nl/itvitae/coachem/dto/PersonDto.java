package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Person;

public record PersonDto(Long id, String name, String address, String city, String zipcode, String phonenumber, UserDto user) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Person, PersonDto> {
    }
}
