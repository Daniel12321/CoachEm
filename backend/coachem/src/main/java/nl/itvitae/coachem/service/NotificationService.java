package nl.itvitae.coachem.service;

import jakarta.transaction.Transactional;
import nl.itvitae.coachem.dto.*;
import nl.itvitae.coachem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private InfoChangeService infoChangeService;

    public NotificationsDto getNotification() {
        User user = User.getFromAuth();

        List<EvaluationDto> attendees = evaluationService.getAllUnseenAttendees(user.getId());
        List<InviteDto> invites = inviteService.getAllUnseen(user.getId());
        List<EvaluationDto> evaluations;
        List<FeedbackDto> feedback;
        List<InfoChangeDto> infoChanges;

        if (user.getRole().equalsIgnoreCase("TRAINEE")) {
            evaluations = evaluationService.getAllUnseen(user.getId());
            feedback = feedbackService.getAllUnseen(user.getId());
        } else {
            evaluations = List.of();
            feedback = List.of();
        }

        if (user.getRole().equalsIgnoreCase("HR")) {
            infoChanges = infoChangeService.getAllInfoChanges();
        } else {
            infoChanges = List.of();
        }

        return new NotificationsDto(attendees, invites, evaluations, feedback, infoChanges);
    }
}
