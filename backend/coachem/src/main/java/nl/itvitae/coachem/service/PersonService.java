package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.model.Person;
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
    private PersonDto.Mapper mapper;

    public List<PersonDto> getAllPersons() {
        Iterable<Person> people = personRepository.findAll();
        List<PersonDto> dtos = new ArrayList<>();
        people.forEach(person -> dtos.add(mapper.get(person)));
        return dtos;
    }
}
