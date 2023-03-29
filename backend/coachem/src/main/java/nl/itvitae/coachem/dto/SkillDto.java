package nl.itvitae.coachem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.itvitae.coachem.model.Category;
import nl.itvitae.coachem.model.Skill;

public record SkillDto(Long id, String name, Boolean type, String description, String time, Integer duration, CategoryDto category) {

    @JsonIgnore
    public boolean isValid() {
        return name != null &&
                type != null &&
                duration != null &&
                time != null &&
                description != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Skill, SkillDto> {
    }
}
