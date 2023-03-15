package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.dto.TraineeSkillDTO;
import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.model.TraineeSkill;
import nl.itvitae.coachem.repository.SkillRepository;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TraineeSkillService {

    @Autowired
    TraineeSkillRepository traineeSkillRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    TraineeSkillDTO.Mapper mapper;

    public TraineeSkillDTO newTraineeSkill(TraineeSkillDTO traineeSkillDTO) {
       if(traineeSkillDTO.time() == null || traineeSkillDTO.completed() == null){
           return null;
       }

        TraineeSkill traineeSkill = traineeSkillRepository.save(mapper.post(traineeSkillDTO));
       return mapper.get(traineeSkill);
    }

    public TraineeSkillDTO getTraineeSkillById(Long id) {
        return mapper.get(traineeSkillRepository.findById(id).get());
    }

    public boolean deleteTraineeSkillById(Long id) {
        if (traineeSkillRepository.existsById(id)) {
            traineeSkillRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<TraineeSkillDTO> updateTraineeSkillById(TraineeSkillDTO traineeSkillDTO, Long traineeSkillid) {
        if(!traineeSkillRepository.existsById(traineeSkillid)){
            return Optional.empty();
        }
        TraineeSkill traineeSkill = traineeSkillRepository.save(
                mapper.update(traineeSkillDTO,
                        traineeSkillRepository.findById(traineeSkillid).get()));

        return Optional.of(mapper.get(traineeSkill));
    }


    public Boolean addSkillToTraineeSkill(Long traineeSkillId, Long skillId) {
        if(!traineeSkillRepository.existsById(traineeSkillId) || !skillRepository.existsById(skillId)){
            return false;
        }
        TraineeSkill tempTraineeSkill = traineeSkillRepository.findById(traineeSkillId).get();
        Skill tempSkill = skillRepository.findById(skillId).get();
        tempTraineeSkill.setSkill(tempSkill);
        tempSkill.getTraineeSkills().add(tempTraineeSkill);
        traineeSkillRepository.save(tempTraineeSkill);
        return true;
    }



}
