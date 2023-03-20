package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Evaluation;

import java.util.List;

public record EvaluationDto(Long id, String time, PersonDto trainee, List<PersonDto> attendees) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Evaluation, EvaluationDto> {
    }
}
