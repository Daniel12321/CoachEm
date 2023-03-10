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
    public List<EvaluationDto> getEvaluationsByTraineeId(@PathVariable(value = "personid") Long personId) {
        return evaluationService.getEvaluationsByTraineeId(personId);
    }

    @GetMapping("/attendee/{personid}")
    public List<EvaluationDto> getEvaluationsByAttendeeId(@PathVariable(value = "personid") Long personId) {
        return evaluationService.getEvaluationsByAttendeeId(personId);
    }

    @PostMapping("/new/{attendeeid}/{traineeid}")
    public EvaluationDto addEvaluation(@RequestBody EvaluationDto evaluationDto, @PathVariable(value = "attendeeid") Long attendeeId, @PathVariable(value = "traineeid") Long traineeId) {
        return evaluationService.addEvaluation(evaluationDto, attendeeId, traineeId);
    }

    @DeleteMapping("/delete/{evaluationid}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable(value = "evaluationid") Long evaluationId) {
        if (evaluationService.deleteEvaluationById(evaluationId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
