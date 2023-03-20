package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.ProgressDto;
import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.Progress;
import nl.itvitae.coachem.model.TraineeSkill;
import nl.itvitae.coachem.model.User;
import nl.itvitae.coachem.repository.ProgressRepository;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProgressService {

    @Autowired
    ProgressRepository progressRepository;

    @Autowired
    TraineeSkillRepository traineeSkillRepository;

    @Autowired
    ProgressDto.Mapper mapper;

    public Optional<ProgressDto> newProgress(ProgressDto dto, Long traineeSkillId) {
        if (!dto.isValid())
            return Optional.empty();

        TraineeSkill skill = traineeSkillRepository.findById(traineeSkillId).orElse(null);
        if (skill == null)
            return Optional.empty();

        Progress progress = mapper.post(dto);
        progress.setTraineeSkill(skill);
        return Optional.of(mapper.get(progressRepository.save(progress)));
    }

    public List<ProgressDto> getProgressByTraineeSkill(Long id) {
        return progressRepository.findByTraineeSkillId(id).stream().map(mapper::get).toList();
    }

    public boolean deleteProgress(Long id) {
        if (progressRepository.existsById(id)) {
            progressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
