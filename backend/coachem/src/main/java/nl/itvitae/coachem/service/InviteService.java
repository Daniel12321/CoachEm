package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InviteDto;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InviteService {
    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private InviteDto.Mapper mapper;
}
