package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.model.InfoChange;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.Skill;
import nl.itvitae.coachem.repository.InfoChangeRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import nl.itvitae.coachem.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ListUtil.toList(personRepository.findAll())
                .stream()
                .map(mapper::get)
                .toList();
    }

    public Optional<PersonDto> acceptInfoChange(Long infoChangeId) {
        Optional<InfoChange> change = infoChangeRepository.findById(infoChangeId);
        if (change.isEmpty()) {
            return Optional.empty();
        }

        InfoChange infoChange = change.get();
        Person person = infoChange.getPerson();

        PersonDto personDto = new PersonDto(
                null,
                infoChange.getName(),
                infoChange.getAddress(),
                infoChange.getCity(),
                infoChange.getZipcode(),
                infoChange.getPhonenumber(),
                null);
        infoChangeService.deleteInfoChangeById(infoChangeId);
        person = personRepository.save(mapper.update(personDto, person));
        return Optional.of(mapper.get(person));
    }

    public boolean deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            return false;
        }
        personRepository.deleteById(id);
        return true;
    }

    public List<PersonDto> getAllTrainees() {
        return ListUtil.toList(personRepository.findByUser_Role("TRAINEE"))
                .stream()
                .map(mapper::get)
                .toList();
    }

    public PersonDto getPersonById(Long id) {
        return mapper.get(personRepository.findById(id).get());
    }


    public Optional<PersonDto> updatePersonById(PersonDto personDto, Long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null)
            return Optional.empty();

        person = personRepository.save(mapper.update(personDto, person));
        return Optional.of(mapper.get(person));
    }



}
