package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.FeedbackDto;
import nl.itvitae.coachem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/feedback")
public class FeedBackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/new/{userId}/{traineeSkillId}")
    public ResponseEntity<FeedbackDto> newFeedback(@PathVariable("userId") Long userId,
                                                   @PathVariable("traineeSkillId") Long traineeSkillId,
                                                   @RequestBody FeedbackDto feedback) {
        return feedbackService.newFeedback(feedback, userId, traineeSkillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("id") Long id) {
        return feedbackService.getFeedback(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/traineeskill/{id}")
    public List<FeedbackDto> getFeedbackByTraineeSkill(@PathVariable("id") Long id) {
        return feedbackService.getFeedbackByTraineeSkill(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackDto> updateSkill(@PathVariable("id") Long id, @RequestBody FeedbackDto feedback) {
        return feedbackService.updateFeedback(feedback, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable("id") Long id) {
        if (feedbackService.deleteFeedback(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
