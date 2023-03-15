package nl.itvitae.coachem.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import nl.itvitae.coachem.model.*;

import java.util.ArrayList;
import java.util.List;

public record TraineeSkillDTO(Long id, String progress, String report,
                              SkillDTO skill, UserDto user, String time, Boolean completed) {
    @org.mapstruct.Mapper(componentModel = "spring")
    public interface Mapper extends IEntityMapper<TraineeSkill, TraineeSkillDTO> {    }
}







