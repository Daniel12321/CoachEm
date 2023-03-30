package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.ProgressDto;
import nl.itvitae.coachem.dto.RecommendationDto;
import nl.itvitae.coachem.model.*;
import nl.itvitae.coachem.repository.PersonRepository;
import nl.itvitae.coachem.repository.RecommendationRepository;
import nl.itvitae.coachem.repository.SkillRepository;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private TraineeSkillRepository traineeSkillRepository;

    @Autowired
    private RecommendationDto.Mapper mapper;

    public List<RecommendationDto> getRecommendationsByTraineeId(Long id) {
        return recommendationRepository.findByPersonId(id).stream().map(mapper::get).toList();
    }

    public RecommendationDto newRecommendation(Long personId, Long skillId) {
        Person person = personRepository.findById(personId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "person not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "skill not found"));
        Recommendation recommendation = new Recommendation();

        boolean hasSkill = traineeSkillRepository.findByUserId(personId).stream().anyMatch((traineeskill)-> traineeskill.getSkill().getId().equals(skillId));
        boolean hasRecommendation = recommendationRepository.findByPersonId(personId).stream().anyMatch((recommend)-> recommend.getSkill().getId().equals(skillId));
        if (hasSkill){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "person already has skill");
        }
        if(hasRecommendation){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "person already has recommendation");
        }

        recommendation.setSkill(skill);
        recommendation.setPerson(person);

        return mapper.get(recommendationRepository.save(recommendation));
    }

    public boolean deleteRecommendation(Long id) {
        if (recommendationRepository.existsById(id)) {
            recommendationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
