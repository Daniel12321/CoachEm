package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationDto.Mapper mapper;
}
