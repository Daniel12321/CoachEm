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
    public FeedbackDTO newFeedback(@RequestBody FeedbackDTO feedBackDTO){
        return feedbackService.newFeedback(feedBackDTO);
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

//    @PutMapping("/add_person_to_feedback/{feedbackId}/{personId}")
//    public ResponseEntity<Void> addPersonToFeedback(@PathVariable(value = "feedbackId")Long feedbackId, @PathVariable(value = "personId") Long personId) {
//        if (feedbackService.addPersonToFeedback(feedbackId, personId)) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping("/add_traineeskill_to_feedback/{feedbackId}/{traineeSkillId}")
    public ResponseEntity<Void> addTraineeSkillToFeedback(@PathVariable(value = "feedbackId")Long feedbackId, @PathVariable(value = "traineeSkillId") Long traineeSkillId ){
        if (feedbackService.addTraineeSkillToFeedback(feedbackId,traineeSkillId)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
