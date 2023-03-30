package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.ISkillAPI;
import nl.itvitae.coachem.dto.SkillDto;
import nl.itvitae.coachem.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SkillController implements ISkillAPI {

    @Autowired
    private SkillService skillService;

    @Override
    public ResponseEntity<SkillDto> newSkill(@RequestBody SkillDto skill, @PathVariable("categoryId") Long id) {
        return skillService.newSkill(skill, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public List<SkillDto> getAllSkills() {
        return skillService.getAllSkills();
    }

    @Override
    public ResponseEntity<SkillDto> updateSkillById(@PathVariable("id") Long id, @RequestBody SkillDto skill) {
        return skillService.updateSkillById(skill, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteSkillById(@PathVariable("id") Long id) {
        if (skillService.deleteSkillById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
