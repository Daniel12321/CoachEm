package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.repository.InfoChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InfoChangeService {
    @Autowired
    private InfoChangeRepository infoChangeRepository;

    @Autowired
    private InfoChangeDto.Mapper mapper;
}
