package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.dto.TraineeSkillDTO;
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
    TraineeSkillService traineeSkillService;

    @PostMapping("/new")
    public TraineeSkillDTO newTraineeSkill(@RequestBody TraineeSkillDTO traineeSkillDto) {
        return traineeSkillService.newTraineeSkill(traineeSkillDto);
    }

    @GetMapping("/by_id/{id}")
    public TraineeSkillDTO getTraineeSkillById(@PathVariable(value = "id") Long id) {
        return traineeSkillService.getTraineeSkillById(id);
    }

    @GetMapping("/by_user/{id}")
    public List<TraineeSkillDTO> getTraineeSkillByUser(@PathVariable(value = "id") Long id) {
        return traineeSkillService.getTraineeSkillByUser(id);
    }


    // TODO: 15/03/2023 // api/traineeskill/1/user/2 

    @PutMapping("/add_skill_to_traineeskill/traineeskillid{traineeSkillId}/skillid{skillId}")
    public ResponseEntity<Void> addSkillToTraineeSkill(@PathVariable(value = "traineeSkillId") Long traineeSkillId, @PathVariable(value = "skillId") Long skillId) {
        if (traineeSkillService.addSkillToTraineeSkill(traineeSkillId, skillId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/add_user_to_traineeskill/traineeskillid{traineeSkillId}/userid{userId}")
    public ResponseEntity<Void> addUserToTraineeSkill(@PathVariable(value = "traineeSkillId") Long traineeSkillId, @PathVariable(value = "userId") Long userId) {
        if (traineeSkillService.addUserToTraineeSkill(traineeSkillId, userId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/by_id/{id}")
    public ResponseEntity<Void> deleteTraineeSkillById(@PathVariable(value = "id") Long id) {
        if (traineeSkillService.deleteTraineeSkillById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/by_id/{id}")
    public ResponseEntity<TraineeSkillDTO> updateTraineeSkillById(@PathVariable(value = "id") Long id, @RequestBody TraineeSkillDTO traineeSkillDTO) {
        return traineeSkillService.updateTraineeSkillById(traineeSkillDTO, id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
