package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/evaluation")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{personid}")
    public List<EvaluationDto> getEvaluationsByPersonId(@PathVariable(value = "personid") long id){
        return evaluationService.getEvaluationsByPersonId(id);
    }

    @PostMapping("/new/{attendeeid}/{traineeid}")
    public EvaluationDto addEvaluation(@RequestBody EvaluationDto evaluationDto, @PathVariable(value = "attendeeid") long attendeeId, @PathVariable(value = "traineeid") long traineeId){
        return evaluationService.addEvaluation(evaluationDto, attendeeId, traineeId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEvaluation(@PathVariable(value = "id") long id){
        evaluationService.deleteEvaluation(id);
    }
}
