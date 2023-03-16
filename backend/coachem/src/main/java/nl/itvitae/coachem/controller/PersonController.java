package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.service.InfoChangeService;
import nl.itvitae.coachem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @PutMapping("/infochange/{infochangeid}")
    public ResponseEntity<PersonDto> acceptInfoChange(@PathVariable("infochangeid") Long infoChangeId) {
        return personService.acceptInfoChange(infoChangeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") Long id) {
        if (personService.deletePerson(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
