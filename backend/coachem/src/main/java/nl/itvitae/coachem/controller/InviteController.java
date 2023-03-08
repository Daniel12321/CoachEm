package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;
}
