package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.TraineeSkillDto;
import nl.itvitae.coachem.service.TraineeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/traineeskill")
public class TraineeSkillController {

    @Autowired
    private TraineeSkillService traineeSkillService;

    @PostMapping("/new")
    public ResponseEntity<TraineeSkillDto> newTraineeSkill(@RequestBody TraineeSkillDto traineeSkill,
                                           @PathVariable("traineeId") Long traineeId,
                                           @PathVariable("skillId") Long skillId) {
        return traineeSkillService.newTraineeSkill(traineeSkill, traineeId, skillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TraineeSkillDto> getTraineeSkillById(@PathVariable("id") Long id) {
        return traineeSkillService.getTraineeSkillById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public List<TraineeSkillDto> getTraineeSkillByUser(@PathVariable("id") Long id) {
        return traineeSkillService.getTraineeSkillByUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TraineeSkillDto> updateTraineeSkillById(@PathVariable("id") Long id, @RequestBody TraineeSkillDto traineeSkill) {
        return traineeSkillService.updateTraineeSkillById(traineeSkill, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTraineeSkillById(@PathVariable("id") Long id) {
        if (traineeSkillService.deleteTraineeSkillById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
