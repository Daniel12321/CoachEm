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

    @PostMapping("/new/{traineeid}")
    public EvaluationDto addEvaluation(@RequestBody EvaluationDto evaluation, @PathVariable("traineeid") Long traineeId) {
        return evaluationService.addEvaluation(evaluation, traineeId);
    }

    @PostMapping("/{id}/{attendeeid}")
    public EvaluationDto addEvaluationAttendee(@PathVariable("id") Long evaluationId, @PathVariable("attendeeid") Long attendeeId) {
        return evaluationService.addAttendee(evaluationId, attendeeId);
    }

    @GetMapping("/trainee")
    public List<EvaluationDto> getEvaluationsAsTrainee() {
        return evaluationService.getEvaluationsAsTrainee();
    }

    @GetMapping("/attendee")
    public List<EvaluationDto> getEvaluationsAsAttendee() {
        return evaluationService.getEvaluationsAsAttendee();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EvaluationDto> updateEvaluation(@RequestBody EvaluationDto evaluation, @PathVariable("id") Long id) {
        return evaluationService.updateEvaluation(evaluation, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}/{attendeeid}")
    public void deleteEvaluationAttendee(@PathVariable("id") Long id, @PathVariable("attendeeid") Long attendeeId) {
        this.evaluationService.deleteAttendee(id, attendeeId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEvaluation(@PathVariable("id") Long id) {
        this.evaluationService.deleteEvaluation(id);
    }
}
