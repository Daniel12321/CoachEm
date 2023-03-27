package nl.itvitae.coachem.dto;

import java.util.List;

public record NotificationsDto(List<EvaluationDto> attendees,
                               List<InviteDto> invites,
                               List<EvaluationDto> evaluations,
                               List<FeedbackDto> feedback,
                               List<InfoChangeDto> infoChanges) {
}
