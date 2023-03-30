package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.SkillDto;
import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.repository.CategoryRepository;
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
    private SkillRepository skillRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillDto.Mapper mapper;

    public Optional<SkillDto> newSkill(SkillDto dto, Long id) {
        if (!dto.isValid() || categoryRepository.findById(id).isEmpty())
            return Optional.empty();

        Skill skill = mapper.post(dto);
        skill.setCategory(categoryRepository.findById(id).get());
        skillRepository.save(skill);
        return Optional.of(mapper.get(skill));
    }

    public Optional<SkillDto> getSkillById(Long id) {
        return skillRepository.findById(id).map(mapper::get);
    }

    public List<SkillDto> getAllSkills() {
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

    public Optional<SkillDto> updateSkillById(SkillDto dto, Long id) {
        Skill skill = skillRepository.findById(id).orElse(null);
        if (skill == null)
            return Optional.empty();

        skill = skillRepository.save(mapper.update(dto, skill));
        return Optional.of(mapper.get(skill));
    }
}
