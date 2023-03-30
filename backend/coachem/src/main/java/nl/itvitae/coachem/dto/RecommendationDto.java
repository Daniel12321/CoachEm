package nl.itvitae.coachem.dto;

import nl.itvitae.coachem.model.Recommendation;

public record RecommendationDto(Long id, PersonDto person, SkillDto skill) {

    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<Recommendation, RecommendationDto> {
    }
}
