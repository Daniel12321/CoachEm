package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.FeedbackDTO;
import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/feedback")
public class FeedBackController {

    @Autowired
    FeedbackService feedbackService;


    @PostMapping("/new")
    public void newFeedback(@RequestBody FeedbackDTO feedBackDTO){
        feedbackService.newFeedback(feedBackDTO);
    }

    @GetMapping("/by_id/{id}")
    public FeedbackDTO getFeedbackById(@PathVariable(value = "id")Long id){
        return feedbackService.getFeedbackById(id);
    }

    @PutMapping("update/by_id/{id}")
    public ResponseEntity<FeedbackDTO> updateSkillById(@PathVariable(value = "id") Long id, @RequestBody FeedbackDTO feedbackDTO){
        return feedbackService.updateFeedbackById(feedbackDTO,id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/by_id/{id}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable(value = "id") Long id) {
        if (feedbackService.deleteFeedbackById(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
