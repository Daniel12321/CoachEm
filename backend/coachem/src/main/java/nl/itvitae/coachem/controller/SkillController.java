package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.SkillDto;
import nl.itvitae.coachem.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/new/{categoryId}")
    public ResponseEntity<SkillDto> newSkill(@RequestBody SkillDto skill, @PathVariable("categoryId") Long id) {
        return skillService.newSkill(skill, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<SkillDto> getAllSkills() {
        return skillService.getAllSkills();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SkillDto> updateSkillById(@PathVariable("id") Long id, @RequestBody SkillDto skill) {
        return skillService.updateSkillById(skill, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSkillById(@PathVariable("id") Long id) {
        if (skillService.deleteSkillById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
