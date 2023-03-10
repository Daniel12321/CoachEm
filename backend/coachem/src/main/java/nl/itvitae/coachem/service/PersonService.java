package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InfoChangeDto;
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
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InfoChangeRepository infoChangeRepository;
    @Autowired
    private InfoChangeService infoChangeService;
    @Autowired
    private PersonDto.Mapper mapper;

    @Autowired
    private InfoChangeDto.Mapper infoChangeMapper;

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

    public Optional<PersonDto> acceptInfoChange(Long infoChangeId) {
        if (infoChangeRepository.findById(infoChangeId).isEmpty()) {
            return Optional.empty();
        }
        InfoChange infoChange = infoChangeRepository.findById(infoChangeId).get();
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
        infoChangeService.deleteInfoChangeById(infoChangeId);
        person = personRepository.save(mapper.update(personDto, person));
        return Optional.of(mapper.get(person));
    }

    public boolean deletePersonById(Long id) {
        if (!personRepository.existsById(id)) {
            return false;
        }
        personRepository.deleteById(id);
        return true;
    }
}
