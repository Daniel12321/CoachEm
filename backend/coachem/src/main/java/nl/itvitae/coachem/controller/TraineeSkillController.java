package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.dto.TraineeSkillDTO;
import nl.itvitae.coachem.service.TraineeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/traineeskill")
public class TraineeSkillController {

    @Autowired
    TraineeSkillService traineeSkillService;

    @PostMapping("/new")
    public TraineeSkillDTO newTraineeSkill(@RequestBody TraineeSkillDTO traineeSkillDto){
        return traineeSkillService.newTraineeSkill(traineeSkillDto);
    }

    @GetMapping("/by_id/{id}")
    public TraineeSkillDTO getTraineeSkillById(@PathVariable(value = "id")Long id){
        return traineeSkillService.getTraineeSkillById(id);
    }

    @PutMapping("/add_skill_to_traineeskill/{traineeSkillId}/{skillId}")
    public void getTraineeSkillById(@PathVariable(value = "traineeSkillId")Long traineeSkillId, @PathVariable(value = "skillId") Long skillId){
         traineeSkillService.addSkillToTraineeSkill(traineeSkillId, skillId);
    }

    @DeleteMapping("/delete/by_id/{id}")
    public ResponseEntity<Void> deleteTraineeSkillById(@PathVariable(value = "id") Long id) {
         if(traineeSkillService.deleteTraineeSkillById(id)){
            return ResponseEntity.ok().build();
        } else {
             return ResponseEntity.notFound().build();
         }
    }



    @PutMapping("update/by_id/{id}")
    public ResponseEntity<TraineeSkillDTO> updateTraineeSkillById(@PathVariable(value = "id") Long id, @RequestBody TraineeSkillDTO traineeSkillDTO){
        return traineeSkillService.updateTraineeSkillById(traineeSkillDTO,id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
