package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.InviteDto;
import nl.itvitae.coachem.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;

    @GetMapping("/sentinvites/{personid}")
    public List<InviteDto> getSentInvitesByPersonId(@PathVariable(value = "personid") Long personId) {
        return inviteService.getSentInvitesByPersonId(personId);
    }

    @GetMapping("/receivedinvites/{personid}")
    public List<InviteDto> getReceivedInvitesByPersonId(@PathVariable(value = "personid") Long personId) {
        return inviteService.getReceivedInvitesByPersonId(personId);
    }

    @GetMapping("/accept/{inviteid}")
    public void acceptInviteRequest(@PathVariable(value = "inviteid") Long inviteId) {
        inviteService.acceptInviteRequest(inviteId);
    }

    @PostMapping("/new/{invitesenderid}/{invitereceiverid}")
    public InviteDto addInvite(@RequestBody InviteDto inviteDto, @PathVariable(value = "invitesenderid") Long inviteSenderId, @PathVariable(value = "invitereceiverid") Long inviteReceiverId) {
        return inviteService.addInvite(inviteDto, inviteSenderId, inviteReceiverId);
    }

    @DeleteMapping("/delete/{inviteid}")
    public ResponseEntity<Void> deleteInviteById(@PathVariable(value = "inviteid") Long inviteId) {
        if (inviteService.deleteInviteById(inviteId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}