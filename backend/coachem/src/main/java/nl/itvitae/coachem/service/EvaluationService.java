package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.repository.EvaluationRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EvaluationDto.Mapper mapper;

    public List<EvaluationDto> getEvaluationsByTraineeId(Long personId) {
        return evaluationRepository.findByTraineeId(personId)
                .stream()
                .map(mapper::get)
                .toList();
    }

    public List<EvaluationDto> getEvaluationsByAttendeeId(Long personId) {
        return evaluationRepository.findByAttendeeId(personId)
                .stream()
                .map(mapper::get)
                .toList();
    }

    public Optional<EvaluationDto> addEvaluation(EvaluationDto dto, Long attendeeId, Long traineeId) {
        Person attendee = personRepository.findById(attendeeId).orElse(null);
        Person trainee = personRepository.findById(traineeId).orElse(null);
        if (attendee == null || trainee == null)
            return Optional.empty();

        Evaluation evaluation = mapper.post(dto);
        evaluation.setAttendee(attendee);
        evaluation.setTrainee(trainee);
        evaluation = evaluationRepository.save(evaluation);
        return Optional.of(mapper.get(evaluation));
    }

    public boolean deleteEvaluationById(Long id) {
        if (!evaluationRepository.existsById(id)) {
            return false;
        }
        evaluationRepository.deleteById(id);
        return true;
    }
}
