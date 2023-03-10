package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.repository.EvaluationRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EvaluationDto.Mapper mapper;

    public List<EvaluationDto> getEvaluationsByPersonId(long id) {
        if (personRepository.findById(id).isEmpty()) {
            return null;
        }
        Person person = personRepository.findById(id).get();
        Iterable<Evaluation> evaluations = evaluationRepository.findAll();
        List<EvaluationDto> dtos = new ArrayList<>();
        evaluations.forEach(evaluation -> {
            if (evaluation.getTrainee() == person || evaluation.getAttendee() == person) {
                dtos.add(mapper.get(evaluation));
            }
        });
        return dtos;
    }

    public EvaluationDto addEvaluation(EvaluationDto evaluationDto, long attendeeId, long traineeId) {
        if (evaluationDto.time() == null ||
                personRepository.findById(attendeeId).isEmpty() ||
                personRepository.findById(traineeId).isEmpty()){
            return null;
        }
        Person attendee = personRepository.findById(attendeeId).get();
        Person trainee = personRepository.findById(traineeId).get();
        Evaluation evaluation = mapper.post(evaluationDto);
        evaluation.setAttendee(attendee);
        evaluation.setTrainee(trainee);
        evaluation = evaluationRepository.save(evaluation);
        attendee.getEvaluatingAttendees().add(evaluation);
        trainee.getEvaluatedTrainees().add(evaluation);
        personRepository.save(attendee);
        personRepository.save(trainee);
        return mapper.get(evaluation);
    }

    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}
