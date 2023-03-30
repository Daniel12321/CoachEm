package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IProgressAPI;
import nl.itvitae.coachem.dto.ProgressDto;
import nl.itvitae.coachem.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ProgressController implements IProgressAPI {

    @Autowired
    private ProgressService progressService;

    @Override
    public ResponseEntity<ProgressDto> newProgress(@PathVariable("traineeSkillId") Long traineeSkillId,
                                                   @RequestBody ProgressDto progress) {
        return progressService.newProgress(progress, traineeSkillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public List<ProgressDto> getProgressByTraineeSkill(@PathVariable("id") Long id) {
        return progressService.getProgressByTraineeSkill(id);
    }

    @Override
    public ResponseEntity<Void> deleteProgressById(@PathVariable("id") Long id) {
        if (progressService.deleteProgress(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
