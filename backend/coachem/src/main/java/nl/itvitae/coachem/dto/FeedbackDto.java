package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Feedback;

public record FeedbackDto(Long id, String time, String text, TraineeSkillDto traineeSkill, UserDto user) {

    public boolean isValid() {
        return time != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Feedback, FeedbackDto> {}
}
