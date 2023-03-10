package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.service.InfoChangeService;
import nl.itvitae.coachem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private InfoChangeService infoChangeService;

    @GetMapping("/all")
    public List<PersonDto> getAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping("/new")
    public PersonDto addPerson (@RequestBody PersonDto personDto){
        return personService.addPerson(personDto);
    }

    @PutMapping("/infochange/{infochangeid}")
    public PersonDto acceptInfoChange (@PathVariable(value = "infochangeid") Long id){
        return personService.acceptInfoChange(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePersonById(@PathVariable(value = "id") long id){
        personService.deletePersonById(id);
    }

}
