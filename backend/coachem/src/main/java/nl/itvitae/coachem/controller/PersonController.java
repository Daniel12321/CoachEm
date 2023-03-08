package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/all")
    public List<PersonDto> getAllPersons(){
        return personService.getAllPersons();
    }
}
