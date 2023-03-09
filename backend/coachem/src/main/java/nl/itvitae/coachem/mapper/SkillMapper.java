package nl.itvitae.coachem.mapper;

import nl.itvitae.coachem.dto.SkillGetDto;
import nl.itvitae.coachem.dto.SkillPostDto;
import nl.itvitae.coachem.model.Skill;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillGetDto toDto(Skill skill);
    Skill toEntity(SkillPostDto skill);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkill(SkillPostDto skillDto, @MappingTarget Skill skill);

}
