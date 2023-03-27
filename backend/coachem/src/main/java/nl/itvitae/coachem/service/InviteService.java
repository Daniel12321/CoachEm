package nl.itvitae.coachem.service;

import nl.itvitae.coachem.dto.InviteDto;
import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.repository.InviteRepository;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return inviteRepository.findByInvitedId(personId)
                .stream()
                .map(mapper::get)
                .toList();
    }

    public List<InviteDto> getSentInvitesByPersonId(Long personId) {
        return inviteRepository.findByTraineeId(personId)
                .stream()
                .map(mapper::get)
                .toList();
    }

    public Optional<InviteDto> addInvite(InviteDto dto, Long senderId, Long receiverId) {
        Person inviter = personRepository.findById(senderId).orElse(null);
        Person invited = personRepository.findById(receiverId).orElse(null);
        if (inviter == null || invited == null)
            return Optional.empty();

        Invite invite = mapper.post(dto);
        invite.setAccepted(false);
        invite.setTrainee(inviter);
        invite.setInvited(invited);
        Invite invite2 = inviteRepository.save(invite);
        return Optional.of(mapper.get(invite2));
    }

    public boolean acceptInviteRequest(Long id) {
        Invite invite = inviteRepository.findById(id).orElse(null);
        if (invite == null)
            return false;

        invite.setAccepted(true);
        inviteRepository.save(invite);

        return true;
    }

    public boolean deleteInviteById(Long id) {
        if (!inviteRepository.existsById(id)) {
            return false;
        }
        inviteRepository.deleteById(id);
        return true;
    }

    public List<InviteDto> getAllUnseen(Long personId) {
        return inviteRepository.getAllUnseen(personId).stream().map(mapper::get).toList();
    }
}
