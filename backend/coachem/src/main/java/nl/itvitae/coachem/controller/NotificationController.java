package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.INotificationAPI;
import nl.itvitae.coachem.dto.NotificationsDto;
import nl.itvitae.coachem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class NotificationController implements INotificationAPI {

    @Autowired
    private NotificationService service;

    @Override
    public NotificationsDto getNotifications() {
        return this.service.getNotification();
    }
}
