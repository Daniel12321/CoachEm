package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.TraineeSkill;
import nl.itvitae.coachem.model.User;

public record FeedbackDTO(Long id, TraineeSkillDTO traineeSkill, UserDto user, String time, String text) {
    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Feedback, FeedbackDTO> {    }
}



