package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.FeedbackDto;
import nl.itvitae.coachem.model.Feedback;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.TraineeSkill;
import nl.itvitae.coachem.model.User;
import nl.itvitae.coachem.repository.FeedbackRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private TraineeSkillRepository traineeSkillRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FeedbackDto.Mapper mapper;

    public Optional<FeedbackDto> newFeedback(FeedbackDto dto, Long userId, Long traineeSkillId) {
        if (!dto.isValid())
            return Optional.empty();

        Person person = personRepository.findById(userId).orElse(null);
        TraineeSkill skill = traineeSkillRepository.findById(traineeSkillId).orElse(null);
        if (person == null || skill == null)
            return Optional.empty();

        Feedback feedback = mapper.post(dto);
        feedback.setPerson(person);
        feedback.setTraineeSkill(skill);
        feedback.setNotified(false);
        feedback = feedbackRepository.save(feedback);

        emailService.sendFeedbackEmail(personRepository.findById(skill.getUser().getId()).get(), person, feedback);

        return Optional.of(mapper.get(feedback));
    }

    public Optional<FeedbackDto> getFeedback(Long id) {
        return feedbackRepository.findById(id).map(mapper::get);
    }

    public List<FeedbackDto> getFeedbackByTraineeSkill(Long id) {
        return feedbackRepository.findByTraineeSkillId(id).stream().map(mapper::get).toList();
    }

    public List<FeedbackDto> getAllUnseen(Long personId) {
        return feedbackRepository.getAllUnseen(personId).stream().map(mapper::get).toList();
    }

    public Optional<FeedbackDto> updateFeedback(FeedbackDto dto, Long id) {
        return feedbackRepository
                .findById(id)
                .map(feedback -> mapper.get(feedbackRepository.save(mapper.update(dto, feedback))));
    }

    public boolean deleteFeedback(Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void markAllSeen(Long traineeSkillId) {
        feedbackRepository.markAllSeen(traineeSkillId, User.getFromAuth().getId());
    }
}
