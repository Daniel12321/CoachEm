package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IFeedBackAPI;
import nl.itvitae.coachem.dto.FeedbackDto;
import nl.itvitae.coachem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class FeedBackController implements IFeedBackAPI {

    @Autowired
    private FeedbackService feedbackService;

    @Override
    public ResponseEntity<FeedbackDto> newFeedback(@PathVariable("userId") Long userId,
                                                   @PathVariable("traineeSkillId") Long traineeSkillId,
                                                   @RequestBody FeedbackDto feedback) {
        return feedbackService.newFeedback(feedback, userId, traineeSkillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("id") Long id) {
        return feedbackService.getFeedback(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public List<FeedbackDto> getFeedbackByTraineeSkill(@PathVariable("id") Long id) {
        return feedbackService.getFeedbackByTraineeSkill(id);
    }

    @Override
    public ResponseEntity<FeedbackDto> updateFeedback(@PathVariable("id") Long id, @RequestBody FeedbackDto feedback) {
        return feedbackService.updateFeedback(feedback, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void markAllSeen(@PathVariable("id") Long traineeSkillId) {
        feedbackService.markAllSeen(traineeSkillId);
    }

    @Override
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable("id") Long id) {
        if (feedbackService.deleteFeedback(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
