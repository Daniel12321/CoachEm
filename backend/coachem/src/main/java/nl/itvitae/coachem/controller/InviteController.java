package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.dto.InviteDto;
import nl.itvitae.coachem.model.Invite;
import nl.itvitae.coachem.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;

    @GetMapping("/sentinvites/{personid}")
    public List<InviteDto> getSentInvitesByPersonId(@PathVariable(value = "personid") long id) {
        return inviteService.getSentInvitesByPersonId(id);
    }

    @GetMapping("/receivedinvites/{personid}")
    public List<InviteDto> getReceivedInvitesByPersonId(@PathVariable(value = "personid") long id) {
        return inviteService.getReceivedInvitesByPersonId(id);
    }

    @GetMapping("/accept/{inviteid}")
    public void acceptInviteRequest(@PathVariable(value = "inviteid") long inviteId){
        inviteService.acceptInviteRequest(inviteId);
    }

    @PostMapping("/new/{inviterid}/{invitedid}")
    public InviteDto addInvite(@RequestBody InviteDto inviteDto, @PathVariable(value = "inviterid") long inviterId, @PathVariable(value = "invitedid") long invitedId) {
        return inviteService.addInvite(inviteDto, inviterId, invitedId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteInviteById(@PathVariable(value = "id") long id) {
        inviteService.deleteInviteById(id);
    }
}