package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.*;
import nl.itvitae.coachem.model.*;
import nl.itvitae.coachem.repository.*;
import nl.itvitae.coachem.util.ListUtil;
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
    private TraineeSkillRepository traineeSkillRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private TraineeSkillDto.Mapper traineeskillMapper;

    @Autowired
    private RecommendationDto.Mapper recommendationMapper;

    @Autowired
    private SkillDto.Mapper skillMapper;

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

    public PersonDto getPersonByEmail(String email) {
        return mapper.get(personRepository.findByUserEmail(email).orElse(null));
    }

    public List<TraineeListDto> getTraineesForCoachRecommendations() {
        List<SkillDto> skills = ListUtil.toList(skillRepository.findAll())
                .stream()
                .map(skillMapper::get)
                .toList();

        List<PersonDto> trainees = ListUtil.toList(personRepository.findByUser_Role("TRAINEE"))
                .stream()
                .map(mapper::get)
                .toList();

        List<TraineeListDto> possibleTrainees = new ArrayList<>();
        skills.forEach((skill)->
                possibleTrainees.add(new TraineeListDto(trainees.stream().filter((t)->this.filter(t, skill)).toList())));
        return possibleTrainees;
    }

    private boolean filter(PersonDto t, SkillDto skill){
        return traineeSkillRepository.findByUserId(t.id())
                .stream()
                .noneMatch((traineeskill)-> traineeskill.getSkill().getId().equals(skill.id()))
                &&
                recommendationRepository.findByPersonId((t.id()))
                        .stream().noneMatch((recommendation)-> recommendation.getSkill().getId().equals(skill.id()));
    }
}
