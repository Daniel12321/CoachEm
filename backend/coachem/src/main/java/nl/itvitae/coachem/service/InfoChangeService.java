package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InfoChangeDto;
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
public class InfoChangeService {
    @Autowired
    private InfoChangeRepository infoChangeRepository;
    @Autowired
    private InfoChangeDto.Mapper mapper;
    @Autowired
    private PersonRepository personRepository;

    public List<InfoChangeDto> getAllInfoChanges() {
        Iterable<InfoChange> infoChanges = infoChangeRepository.findAll();
        List<InfoChangeDto> dtos = new ArrayList<>();
        infoChanges.forEach(infoChange -> dtos.add(mapper.get(infoChange)));
        return dtos;
    }

    public InfoChangeDto addInfoChangeRequest(Long id, InfoChangeDto infoChangeDto) {
        if (personRepository.findById(id).isEmpty()) {
            return null;
        }
        Person person = personRepository.findById(id).get();
        InfoChange infoChange = mapper.post(infoChangeDto);
        if (person.getInfoChange() != null) {
            infoChange = mapper.update(mapper.get(infoChange), person.getInfoChange());
            infoChangeRepository.save(infoChange);
            return mapper.get(infoChange);
        }
        infoChange.setPerson(person);
        infoChangeRepository.save(infoChange);
        person.setInfoChange(infoChange);
        personRepository.save(person);
        return mapper.get(infoChange);
    }

    public boolean deleteInfoChangeById(Long id) {
        if (!infoChangeRepository.existsById(id)) {
            return false;
        }
        InfoChange infoChange = infoChangeRepository.findById(id).get();
        Person person = infoChange.getPerson();
        person.setInfoChange(null);
        personRepository.save(person);
        infoChangeRepository.deleteById(id);
        return true;
    }
}
