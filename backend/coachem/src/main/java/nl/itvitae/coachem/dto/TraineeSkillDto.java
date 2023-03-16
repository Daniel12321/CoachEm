package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.TraineeSkill;

public record TraineeSkillDto(Long id, String progress, String report, String time,
                              Boolean completed, SkillDto skill, UserDto user) {

    public boolean isValid() {
        return time != null && completed != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<TraineeSkill, TraineeSkillDto> {
    }
}







