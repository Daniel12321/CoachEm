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

    public List<InviteDto> getReceivedInvitesByPersonId(Long personId) {
        return inviteRepository.findByInvitedPersonId(personId)
                .stream()
                .map(mapper::get)
                .toList();
    }

    public List<InviteDto> getSentInvitesByPersonId(Long personId) {
        return inviteRepository.findByInviterId(personId)
                .stream()
                .map(mapper::get)
                .toList();
    }

    public InviteDto addInvite(InviteDto inviteDto, Long inviteSenderId, Long inviteReceiverId) {
        if (inviteDto.time() == null ||
                personRepository.findById(inviteSenderId).isEmpty() ||
                personRepository.findById(inviteReceiverId).isEmpty()) {
            return null;
        }
        Person inviter = personRepository.findById(inviteSenderId).get();
        Person invited = personRepository.findById(inviteReceiverId).get();
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

    public void acceptInviteRequest(Long inviteId) {
        if (inviteRepository.findById(inviteId).isPresent()) {
            Invite invite = inviteRepository.findById(inviteId).get();
            invite.setAccepted(true);
            inviteRepository.save(invite);
        }
    }

    public boolean deleteInviteById(Long inviteId) {
        if (!inviteRepository.existsById(inviteId)) {
            return false;
        }
        inviteRepository.deleteById(inviteId);
        return true;
    }
}
