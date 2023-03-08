package nl.itvitae.coachem.controller;


import nl.itvitae.coachem.dto.SkillPostDto;
import nl.itvitae.coachem.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/contactPerson")
public class SkillController {

    @Autowired
    SkillService skillService;


    @PostMapping("/new")
    public void newSkill(@RequestBody SkillPostDto skillPostdto){
        skillService.newSkill(skillPostdto);
    }
}
