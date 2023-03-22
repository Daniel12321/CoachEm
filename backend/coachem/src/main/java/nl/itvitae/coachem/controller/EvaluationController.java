package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.dto.NewEvaluationAttendeeDto;
import nl.itvitae.coachem.dto.NewEvaluationDto;
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

    @PostMapping("/new")
    public EvaluationDto addEvaluation(@RequestBody NewEvaluationDto evaluation) {
        return evaluationService.addEvaluation(evaluation);
    }

    @PostMapping("/{id}")
    public EvaluationDto addEvaluationAttendee(@PathVariable("id") Long evaluationId, @RequestBody NewEvaluationAttendeeDto attendee) {
        return evaluationService.addAttendee(evaluationId, attendee);
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
