package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.Person;

public record EvaluationDto(Long id, String time, Person trainee, Person attendee) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Evaluation, EvaluationDto> {
    }
}
