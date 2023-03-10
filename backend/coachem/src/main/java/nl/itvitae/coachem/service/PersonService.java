package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.model.InfoChange;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.repository.InfoChangeRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InfoChangeRepository infoChangeRepository;
    @Autowired
    InfoChangeService infoChangeService;

    @Autowired
    private PersonDto.Mapper mapper;

    public List<PersonDto> getAllPersons() {
        Iterable<Person> people = personRepository.findAll();
        List<PersonDto> dtos = new ArrayList<>();
        people.forEach(person -> dtos.add(mapper.get(person)));
        return dtos;
    }

    public PersonDto addPerson(PersonDto personDto) {
        if (personDto.email() == null ||
                personDto.name() == null ||
                personDto.address() == null ||
                personDto.city() == null ||
                personDto.zipcode() == null ||
                personDto.role() == null) {
            return null;
        }
        Person person = personRepository.save(mapper.post(personDto));
        return mapper.get(person);
    }

    public PersonDto acceptInfoChange(Long id) {
        if(infoChangeRepository.findById(id).isEmpty()){
            return null;
        }
        InfoChange infoChange = infoChangeRepository.findById(id).get();
        Person person = infoChange.getPerson();
        PersonDto personDto = new PersonDto(
                null,
                infoChange.getEmail(),
                infoChange.getName(),
                infoChange.getAddress(),
                infoChange.getCity(),
                infoChange.getZipcode(),
                infoChange.getPhonenumber(),
                person.getRole());
        infoChangeService.deleteInfoChangeById(id);
        return mapper.get(mapper.update(personDto, person));
    }

    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }
}
