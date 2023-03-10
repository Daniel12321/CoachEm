package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.TraineeSkill;

public record FeedbackDTO(Long id, TraineeSkill traineeSkill, Person person) {
    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Feedback, FeedbackDTO> {    }
}



