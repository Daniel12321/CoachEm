package nl.itvitae.coachem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.Progress;

public record ProgressDto(Long id, String time, String text, TraineeSkillDto traineeSkill) {

    @JsonIgnore
    public boolean isValid() {
        return time != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Progress, ProgressDto> {}
}
