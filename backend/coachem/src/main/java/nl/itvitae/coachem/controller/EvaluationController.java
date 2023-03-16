package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/trainee/{personid}")
    public List<EvaluationDto> getEvaluationsByTraineeId(@PathVariable("personid") Long personId) {
        return evaluationService.getEvaluationsByTraineeId(personId);
    }

    @GetMapping("/attendee/{personid}")
    public List<EvaluationDto> getEvaluationsByAttendeeId(@PathVariable("personid") Long personId) {
        return evaluationService.getEvaluationsByAttendeeId(personId);
    }

    @PostMapping("/new/{attendeeid}/{traineeid}")
    public ResponseEntity<EvaluationDto> addEvaluation(@RequestBody EvaluationDto evaluation, @PathVariable("attendeeid") Long attendeeId, @PathVariable("traineeid") Long traineeId) {
        return evaluationService.addEvaluation(evaluation, attendeeId, traineeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable("id") Long id) {
        if (evaluationService.deleteEvaluationById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
