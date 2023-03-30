package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IPersonAPI;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.dto.TraineeListDto;
import nl.itvitae.coachem.service.InfoChangeService;
import nl.itvitae.coachem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PersonController implements IPersonAPI {

    @Autowired
    private PersonService personService;

    @Autowired
    private InfoChangeService infoChangeService;

    @Override
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @Override
    public PersonDto getPersonByEmail(@PathVariable(value = "email") String email){
        return personService.getPersonByEmail(email);
    }

    @Override
    public List<PersonDto> getAllTrainees() {
        return personService.getAllTrainees();
    }

    @Override
    public List<TraineeListDto> getTraineesForCoachRecommendations() {
        return personService.getTraineesForCoachRecommendations();
    }

    @Override
    public PersonDto getPersonById(@PathVariable(value = "id") Long id) {
        return personService.getPersonById(id);
    }

    @Override
    public ResponseEntity<PersonDto> acceptInfoChange(@PathVariable("infochangeid") Long infoChangeId) {
        return personService.acceptInfoChange(infoChangeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") Long id) {
        if (personService.deletePerson(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<PersonDto> updatePersonById(@PathVariable("id") Long id, @RequestBody PersonDto person){
        return personService.updatePersonById(person, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
