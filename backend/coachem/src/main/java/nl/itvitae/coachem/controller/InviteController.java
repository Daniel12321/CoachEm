package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IInviteAPI;
import nl.itvitae.coachem.dto.InviteDto;
import nl.itvitae.coachem.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class InviteController implements IInviteAPI {

    @Autowired
    private InviteService inviteService;

    @Override
    public List<InviteDto> getSentInvitesByPersonId(@PathVariable("personid") Long personId) {
        return inviteService.getSentInvitesByPersonId(personId);
    }

    @Override
    public List<InviteDto> getReceivedInvitesByPersonId(@PathVariable("personid") Long personId) {
        return inviteService.getReceivedInvitesByPersonId(personId);
    }

    @Override
    public ResponseEntity<Void> acceptInviteRequest(@PathVariable("id") Long id) {
        if (inviteService.acceptInviteRequest(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<InviteDto> addInvite(@RequestBody InviteDto invite, @PathVariable("traineeid") Long traineeId, @PathVariable("receiverid") Long receiverId) {
        return inviteService.addInvite(invite, traineeId, receiverId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<Void> deleteInviteById(@PathVariable("id") Long id) {
        if (inviteService.deleteInviteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
