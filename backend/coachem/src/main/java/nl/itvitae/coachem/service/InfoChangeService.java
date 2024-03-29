package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.model.InfoChange;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.User;
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
public class InfoChangeService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private InfoChangeRepository infoChangeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InfoChangeDto.Mapper mapper;

    public List<InfoChangeDto> getAllInfoChanges() {
        return ListUtil.toList(infoChangeRepository.findAll())
                .stream()
                .map(mapper::get)
                .toList();
    }

    public InfoChangeDto addInfoChangeRequest(InfoChangeDto dto) {
        Long id = User.getFromAuth().getId();
        Person person = personRepository.findById(id).orElse(null);

        if (person == null)
            return null;

        InfoChange infoChange = mapper.post(dto);
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

    public boolean deleteInfoChangeById(Long id, boolean sendEmail) {
        InfoChange infoChange = infoChangeRepository.findById(id).orElse(null);
        if (infoChange == null)
            return false;

        Person person = infoChange.getPerson();
        person.setInfoChange(null);
        personRepository.save(person);
        infoChangeRepository.deleteById(id);

        if (sendEmail)
            this.emailService.sendInfoChangeRejectedEmail(person);

        return true;
    }

    public Optional<InfoChangeDto> getInfoChangeById(Long id) {return infoChangeRepository.findById(id).map(mapper::get);}
}
