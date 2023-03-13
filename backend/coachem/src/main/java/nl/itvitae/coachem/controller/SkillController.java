package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.SkillDTO;
import nl.itvitae.coachem.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    SkillService skillService;


    @PostMapping("/new")
    public SkillDTO newSkill(@RequestBody SkillDTO skillDTO){
        return skillService.newSkill(skillDTO);
    }

    @GetMapping("/by_id/{id}")
    public SkillDTO getSkillById(@PathVariable(value = "id")Long id){
        return skillService.getSkillById(id);
    }

    @DeleteMapping("/delete/by_id/{id}")
    public ResponseEntity<Void> deleteSkillById(@PathVariable(value = "id") Long id) {
        if (skillService.deleteSkillById(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update/by_id/{id}")
    public ResponseEntity<SkillDTO> updateSkillById(@PathVariable(value = "id") Long id, @RequestBody SkillDTO skillDTO){
        return skillService.updateSkillById(skillDTO,id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
