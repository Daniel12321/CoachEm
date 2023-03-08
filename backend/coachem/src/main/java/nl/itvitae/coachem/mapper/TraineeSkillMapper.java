package nl.itvitae.coachem.mapper;

import nl.itvitae.coachem.dto.SkillGetDto;
import nl.itvitae.coachem.dto.SkillPostDto;
import nl.itvitae.coachem.models.Skill;
import nl.itvitae.coachem.models.TraineeSkill;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TraineeSkillMapper {
    SkillGetDto toDto(TraineeSkill traineeSkill);
    Skill toEntity(TraineeSkillPostDto traineeSkill);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkill(SkillPostDto skillDto, @MappingTarget Skill skill);

}
