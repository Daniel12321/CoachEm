package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.dto.NotificationsDto;
import nl.itvitae.coachem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping("/all")
    public NotificationsDto getNotifications() {
        return this.service.getNotification();
    }
}
