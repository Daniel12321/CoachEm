package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IEvaluationAPI;
import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.dto.NewEvaluationAttendeeDto;
import nl.itvitae.coachem.dto.NewEvaluationDto;
import nl.itvitae.coachem.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class EvaluationController implements IEvaluationAPI {

    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EvaluationDto addEvaluation(@RequestBody NewEvaluationDto evaluation) {
        return evaluationService.addEvaluation(evaluation);
    }

    @Override
    public EvaluationDto addEvaluationAttendee(@PathVariable("id") Long evaluationId, @RequestBody NewEvaluationAttendeeDto attendee) {
        return evaluationService.addAttendee(evaluationId, attendee);
    }

    @Override
    public List<EvaluationDto> getEvaluationsAsTrainee() {
        return evaluationService.getEvaluationsAsTrainee();
    }

    @Override
    public List<EvaluationDto> getEvaluationsAsAttendee() {
        return evaluationService.getEvaluationsAsAttendee();
    }

    @Override
    public EvaluationDto updateEvaluation(@RequestBody EvaluationDto evaluation, @PathVariable("id") Long id) {
        return evaluationService.updateEvaluation(evaluation, id);
    }

    @Override
    public void markAllSeen() {
        evaluationService.markAllSeen();
    }

    @Override
    public void deleteEvaluationAttendee(@PathVariable("id") Long id, @PathVariable("attendeeid") Long attendeeId) {
        this.evaluationService.deleteAttendee(id, attendeeId);
    }

    @Override
    public void deleteEvaluation(@PathVariable("id") Long id) {
        this.evaluationService.deleteEvaluation(id);
    }
}
