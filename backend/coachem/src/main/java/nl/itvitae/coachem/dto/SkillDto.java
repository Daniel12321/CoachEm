package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Skill;

public record SkillDto(Long id, String name, Boolean type, String description, String time, Integer duration, String category) {

    public boolean isValid() {
        return name != null &&
                type != null &&
                duration != null &&
                time != null &&
                description != null &&
                category != null;
    }

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Skill, SkillDto> {
    }
}
