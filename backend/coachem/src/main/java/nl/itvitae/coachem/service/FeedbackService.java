package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.FeedbackDTO;
import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.model.*;
import nl.itvitae.coachem.repository.FeedbackRepository;
import nl.itvitae.coachem.repository.TraineeSkillRepository;
import nl.itvitae.coachem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    TraineeSkillRepository traineeSkillRepository;

    @Autowired
    FeedbackDTO.Mapper mapper;

    @Autowired
    UserRepository userRepository;

    public FeedbackDTO newFeedback(FeedbackDTO feedBackDTO) {
        if(feedBackDTO.time() == null){
            return null;
        }
        Feedback feedback = feedbackRepository.save(mapper.post(feedBackDTO));
        return mapper.get(feedback);
    }

    public FeedbackDTO getFeedbackById(Long id) {
        return mapper.get(feedbackRepository.findById(id).get());
    }

    public boolean deleteFeedbackById(Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<FeedbackDTO> updateFeedbackById(FeedbackDTO feedbackDTO, Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            return Optional.empty();
        }
        Feedback feedback = feedbackRepository.save(mapper.update(feedbackDTO, feedbackRepository.findById(feedbackId).get()));
        return Optional.of(mapper.get(feedback));
    }

    public Boolean addTraineeSkillToFeedback(Long feedbackId, Long traineeSkillId) {
        if (!feedbackRepository.existsById(feedbackId) || !traineeSkillRepository.existsById(traineeSkillId)) {
            return false;
        }
        Feedback tempFeedback = feedbackRepository.findById(feedbackId).get();
        TraineeSkill tempTraineeSkill = traineeSkillRepository.findById(traineeSkillId).get();
        tempFeedback.setTraineeSkill(tempTraineeSkill);
        tempTraineeSkill.getFeedbacks().add(tempFeedback);
        feedbackRepository.save(tempFeedback);
        return true;
    }

    public Boolean addUserToFeedback(Long feedbackId, Long userId) {
        if (!feedbackRepository.existsById(feedbackId) || !userRepository.existsById(userId)) {
            return false;
        }
        Feedback tempFeedback = feedbackRepository.findById(feedbackId).get();
        User tempUser = userRepository.findById(userId).get();
        tempFeedback.setUser(tempUser);
        tempUser.getFeedbacks().add(tempFeedback);
        feedbackRepository.save(tempFeedback);
        return true;
    }

}