package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.FeedbackDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/feedback")
@Tag(name = "Feedback", description = "Feedback api")
public interface IFeedBackAPI {

    @Operation(
            summary = "Add Feedback",
            description = "Adds feedback to a trainee-skill.",
            tags = {"Feedback"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = FeedbackDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid feedback information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
    })
    @PostMapping(value = "/new/{userId}/{traineeSkillId}", produces = "application/json")
    ResponseEntity<FeedbackDto> newFeedback(@PathVariable("userId") Long userId,
                                                   @PathVariable("traineeSkillId") Long traineeSkillId,
                                                   @RequestBody FeedbackDto feedback);

    @Operation(
            summary = "Get Feedback",
            description = "Gets the feedback by id.",
            tags = {"Feedback"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = FeedbackDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Feedback not found", content = @Content)
    })
    @GetMapping(value = "/get/{id}", produces = "application/json")
    ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("id") Long id);

    @Operation(
            summary = "Get All Feedback for TraineeSkill",
            description = "Gets a list of all feedback from a specified trainee-skill.",
            tags = {"Feedback"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FeedbackDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @GetMapping(value = "/traineeskill/{id}", produces = "application/json")
    List<FeedbackDto> getFeedbackByTraineeSkill(@PathVariable("id") Long id);

    @Operation(
            summary = "Update Feedback",
            description = "Updates a feedback and get the updated version.",
            tags = {"Feedback"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = FeedbackDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Feedback not found", content = @Content)
    })
    @PutMapping(value = "/update/{id}", produces = "application/json")
    ResponseEntity<FeedbackDto> updateFeedback(@PathVariable("id") Long id, @RequestBody FeedbackDto feedback);

    @Operation(
            summary = "Mark All Seen",
            description = "Marks all feedback in which the user is trainee as 'notified'.",
            tags = {"Feedback"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @PutMapping("/seen/{id}")
    void markAllSeen(@PathVariable("id") Long traineeSkillId);

    @Operation(
            summary = "Delete Feedback",
            description = "Deletes a feedback.",
            tags = {"Feedback"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Feedback not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteFeedbackById(@PathVariable("id") Long id);
}
