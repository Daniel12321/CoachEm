package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.repository.SkillRepository;
import nl.itvitae.coachem.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillDTO.Mapper mapper;

    public SkillDTO newSkill(SkillDTO skillDto) {
        if (skillDto.name() == null ||
                skillDto.type() == null ||
                skillDto.duration() == null ||
                skillDto.time() == null ||
                skillDto.description() == null) {
            return null;
        }

        Skill skill = skillRepository.save(mapper.post(skillDto));
        return (mapper.get(skill));
    }

    public SkillDTO getSkillById(Long id) {
        return mapper.get(skillRepository.findById(id).get());
    }

    public List<SkillDTO> getAllSkills() {
        return ListUtil.toList(skillRepository.findAll())
                .stream()
                .map(mapper::get)
                .toList();
    }

    public boolean deleteSkillById(Long id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<SkillDTO> updateSkillById(SkillDTO skillDTO, Long id) {
        if (!skillRepository.existsById(id)) {
            return Optional.empty();
        }
        Skill skill = skillRepository.save(mapper.update(skillDTO, skillRepository.findById(id).get()));
        return Optional.of(mapper.get(skill));
    }

}
