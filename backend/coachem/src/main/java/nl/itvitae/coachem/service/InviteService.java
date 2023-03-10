package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InviteDto;
import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.repository.InviteRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InviteService {
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InviteDto.Mapper mapper;

    public List<InviteDto> getReceivedInvitesByPersonId(long id) {
        if (personRepository.findById(id).isEmpty()) {
            return null;
        }
        Person person = personRepository.findById(id).get();
        Iterable<Invite> invites = inviteRepository.findAll();
        List<InviteDto> dtos = new ArrayList<>();
        invites.forEach(invite -> {
            if (invite.getInvitedPerson() == person) {
                dtos.add(mapper.get(invite));
            }
        });
        return dtos;
    }

    public List<InviteDto> getSentInvitesByPersonId(long id) {
        if (personRepository.findById(id).isEmpty()) {
            return null;
        }
        Person person = personRepository.findById(id).get();
        Iterable<Invite> invites = inviteRepository.findAll();
        List<InviteDto> dtos = new ArrayList<>();
        invites.forEach(invite -> {
            if (invite.getInviter() == person) {
                dtos.add(mapper.get(invite));
            }
        });
        return dtos;
    }

    public InviteDto addInvite(InviteDto inviteDto, long inviterId, Long invitedId) {
        if (inviteDto.time() == null ||
                personRepository.findById(inviterId).isEmpty() ||
                personRepository.findById(invitedId).isEmpty()) {
            return null;
        }
        Person inviter = personRepository.findById(inviterId).get();
        Person invited = personRepository.findById(invitedId).get();
        Invite invite = mapper.post(inviteDto);
        invite.setAccepted(false);
        invite.setInvitedPerson(invited);
        invite.setInviter(inviter);
        inviteRepository.save(invite);
        inviter.getSentInvites().add(invite);
        invited.getReceivedInvites().add(invite);
        personRepository.save(invited);
        personRepository.save(inviter);
        return mapper.get(invite);
    }

    public void acceptInviteRequest(long inviteId) {
        if (inviteRepository.findById(inviteId).isPresent()) {
            Invite invite = inviteRepository.findById(inviteId).get();
            invite.setAccepted(true);
            inviteRepository.save(invite);
        }
    }

    public void deleteInviteById(long id) {
        inviteRepository.deleteById(id);
    }
}
