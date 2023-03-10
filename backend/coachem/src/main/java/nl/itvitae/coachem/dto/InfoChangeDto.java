package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.InfoChange;
import nl.itvitae.coachem.model.Person;

public record InfoChangeDto(Long id, String email, String name, String address, String city, String zipcode,
                            String phonenumber, Person person) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<InfoChange, InfoChangeDto> {
    }
}
