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

    @GetMapping("/sent/{personid}")
    public List<InviteDto> getSentInvitesByPersonId(@PathVariable("personid") Long personId) {
        return inviteService.getSentInvitesByPersonId(personId);
    }

    @GetMapping("/received/{personid}")
    public List<InviteDto> getReceivedInvitesByPersonId(@PathVariable("personid") Long personId) {
        return inviteService.getReceivedInvitesByPersonId(personId);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<Void> acceptInviteRequest(@PathVariable("id") Long id) {
        if (inviteService.acceptInviteRequest(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new/{senderid}/{receiverid}")
    public ResponseEntity<InviteDto> addInvite(@RequestBody InviteDto invite, @PathVariable("senderid") Long senderId, @PathVariable("receiverid") Long receiverId) {
        return inviteService.addInvite(invite, senderId, receiverId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInviteById(@PathVariable("id") Long id) {
        if (inviteService.deleteInviteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}