package nl.itvitae.coachem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.TraineeSkill;

public record TraineeSkillDto(Long id, String report, String time,
                              Boolean completed, SkillDto skill, UserDto user) {
    @JsonIgnore
    public boolean isValid() {
        return time != null && completed != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<TraineeSkill, TraineeSkillDto> {
    }
}







