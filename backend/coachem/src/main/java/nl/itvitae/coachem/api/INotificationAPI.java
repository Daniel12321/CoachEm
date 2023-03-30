package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.dto.NotificationsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/notification")
@Tag(name = "Notification", description = "Notification Api")
public interface INotificationAPI {

    @Operation(
            summary = "Get Notifications",
            description = "Get all notifcations for the logged-in user.",
            tags = {"Notification"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = NotificationsDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/all", produces = "application/json")
    NotificationsDto getNotifications();
}
