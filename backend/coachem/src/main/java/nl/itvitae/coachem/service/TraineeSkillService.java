package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.TraineeSkillDto;
import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.model.TraineeSkill;
import nl.itvitae.coachem.model.User;
import nl.itvitae.coachem.repository.SkillRepository;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import nl.itvitae.coachem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TraineeSkillService {

    @Autowired
    private TraineeSkillRepository traineeSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TraineeSkillDto.Mapper mapper;

    public Optional<TraineeSkillDto> newTraineeSkill(TraineeSkillDto dto, Long traineeId, Long skillId) {
        if (!dto.isValid())
            return Optional.empty();

        User trainee = userRepository.findById(traineeId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);
        if (trainee == null || skill == null)
            return Optional.empty();

        TraineeSkill traineeSkill = mapper.post(dto);
        traineeSkill.setUser(trainee);
        traineeSkill.setSkill(skill);
        return Optional.of(mapper.get(traineeSkillRepository.save(traineeSkill)));
    }

    public Optional<TraineeSkillDto> getTraineeSkillById(Long id) {
        return traineeSkillRepository.findById(id).map(mapper::get);
    }

    public List<TraineeSkillDto> getTraineeSkillByUser(Long userId) {
        return traineeSkillRepository.findByUserId(userId)
                .stream().map(mapper::get).toList();
    }

    public boolean deleteTraineeSkillById(Long id) {
        if (traineeSkillRepository.existsById(id)) {
            traineeSkillRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<TraineeSkillDto> updateTraineeSkillById(TraineeSkillDto dto, Long id) {
        TraineeSkill skill = traineeSkillRepository.findById(id).orElse(null);
        if (skill == null)
            return Optional.empty();

        skill = traineeSkillRepository.save(mapper.update(dto, skill));
        return Optional.of(mapper.get(skill));
    }
}
