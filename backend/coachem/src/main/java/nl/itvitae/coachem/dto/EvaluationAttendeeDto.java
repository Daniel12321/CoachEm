package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.EvaluationAttendee;

public record EvaluationAttendeeDto(Long id, Boolean notified, PersonDto person) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<EvaluationAttendee, EvaluationAttendeeDto> {
    }
}
