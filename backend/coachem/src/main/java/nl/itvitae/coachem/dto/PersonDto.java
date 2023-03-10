package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.InfoChange;
import nl.itvitae.coachem.model.Person;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public record PersonDto(Long id, String email, String name, String address, String city, String zipcode,
                        String phonenumber, String role) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Person, PersonDto> {
    }
}
