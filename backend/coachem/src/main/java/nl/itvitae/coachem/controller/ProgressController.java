package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.FeedbackDto;
import nl.itvitae.coachem.dto.ProgressDto;
import nl.itvitae.coachem.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;

    @PostMapping("/new/{traineeSkillId}")
    public ResponseEntity<ProgressDto> newProgress(@PathVariable("traineeSkillId") Long traineeSkillId,
                                                   @RequestBody ProgressDto progress) {
        return progressService.newProgress(progress, traineeSkillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/traineeskill/{id}")
    public List<ProgressDto> getProgressByTraineeSkill(@PathVariable("id") Long id) {
        return progressService.getProgressByTraineeSkill(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProgressById(@PathVariable("id") Long id) {
        if (progressService.deleteProgress(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
