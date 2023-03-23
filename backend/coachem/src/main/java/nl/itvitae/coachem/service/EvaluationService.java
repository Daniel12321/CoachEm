package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.dto.NewEvaluationAttendeeDto;
import nl.itvitae.coachem.dto.NewEvaluationDto;
import nl.itvitae.coachem.model.Evaluation;
import nl.itvitae.coachem.model.EvaluationAttendee;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.User;
import nl.itvitae.coachem.repository.EvaluationAttendeeRepository;
import nl.itvitae.coachem.repository.EvaluationRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationAttendeeRepository evaluationAttendeeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EvaluationDto.Mapper mapper;

    public EvaluationDto addEvaluation(NewEvaluationDto dto) {
        Person trainee = personRepository.findByUserEmail(dto.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainee not found"));
        Evaluation evaluation = new Evaluation(dto.time(), trainee);
        evaluation.setTrainee(trainee);
        evaluation.setAttendees(new ArrayList<>());
        evaluation.setNotified(false);
        evaluation = evaluationRepository.save(evaluation);

        EvaluationAttendee attendee = new EvaluationAttendee(evaluation, personRepository.findById(User.getFromAuth().getId()).get());
        evaluationAttendeeRepository.save(attendee);

        evaluation.getAttendees().add(attendee);
        evaluation = evaluationRepository.save(evaluation);

        return mapper.get(evaluation);
    }

    public EvaluationDto addAttendee(Long id, NewEvaluationAttendeeDto dto) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evaluation not found"));
        Person person = personRepository.findByUserEmail(dto.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

        if (evaluationAttendeeRepository.existsByEvaluationIdAndPersonId(id, person.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That person is already attending that evaluation");

        EvaluationAttendee attendee = new EvaluationAttendee(evaluation, person);
        evaluationAttendeeRepository.save(attendee);

        person.getEvaluations().add(attendee);
        evaluation.getAttendees().add(attendee);

        personRepository.save(person);
        return mapper.get(evaluationRepository.save(evaluation));
    }

    public List<EvaluationDto> getEvaluationsAsTrainee() {
        return evaluationRepository.findByTraineeId(User.getFromAuth().getId())
                .stream()
                .map(mapper::get)
                .toList();
    }

    public List<EvaluationDto> getEvaluationsAsAttendee() {
        return evaluationAttendeeRepository.findByPersonId(User.getFromAuth().getId())
                .stream()
                .map(EvaluationAttendee::getEvaluation)
                .map(mapper::get).toList();
    }

    public List<EvaluationDto> getAllUnseen(Long personId) {
        return evaluationRepository.getAllUnseen(personId)
                .stream().map(mapper::get).toList();
    }

    public List<EvaluationDto> getAllUnseenAttendees(Long personId) {
        return evaluationAttendeeRepository.getAllUnseen(personId)
                .stream()
                .map(EvaluationAttendee::getEvaluation)
                .map(mapper::get)
                .toList();
    }

    public EvaluationDto updateEvaluation(EvaluationDto dto, Long id) {
        return evaluationRepository
                .findById(id)
                .map(feedback -> mapper.get(evaluationRepository.save(mapper.update(dto, feedback))))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evaluation not found"));
    }

    public void markAllSeen() {
        Long id = User.getFromAuth().getId();
        evaluationRepository.markAllSeen(id);
        evaluationAttendeeRepository.markAllSeen(id);
    }

    public void deleteAttendee(Long id, Long attendeeId) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evaluation not found"));
        Person person = personRepository.findById(attendeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

        evaluationAttendeeRepository.deleteByEvaluationIdAndPersonId(evaluation.getId(), person.getId());
    }

    public void deleteEvaluation(Long id) {
        if (!evaluationRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evaluation not found");

        evaluationRepository.deleteById(id);
    }
}
