package nl.itvitae.coachem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.Evaluation;

import java.util.List;

public record EvaluationDto(Long id, String time, Boolean notified, PersonDto trainee, List<EvaluationAttendeeDto> attendees) {

    @JsonIgnore
    public boolean isValid() {
        return time != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Evaluation, EvaluationDto> {
    }
}
