package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.SkillPostDto;
import nl.itvitae.coachem.mapper.SkillMapper;
import nl.itvitae.coachem.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
@Transactional
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    nl.itvitae.coachem.mapper.SkillMapper SkillMapper;

    public void newSkill(SkillPostDto skillPostdto) {
    }
}
