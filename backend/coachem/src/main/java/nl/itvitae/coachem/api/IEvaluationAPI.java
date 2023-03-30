package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.EvaluationDto;
import nl.itvitae.coachem.dto.NewEvaluationAttendeeDto;
import nl.itvitae.coachem.dto.NewEvaluationDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/evaluation")
@Tag(name = "Evaluation", description = "Evaluation api")
public interface IEvaluationAPI {

    @Operation(
            summary = "Add Evaluation",
            description = "Adds a new evaluation and gets the newly created evaluation.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = EvaluationDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid evaluation information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PostMapping(value = "/new", produces = "application/json")
    EvaluationDto addEvaluation(@RequestBody NewEvaluationDto evaluation);

    @Operation(
            summary = "Add Evaluation Attendee",
            description = "Adds an attendee to an evaluation and gets the evaluation.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = EvaluationDto.class))),
            @ApiResponse(responseCode = "400", description = "Person already an attendee", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Evaluation not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @PostMapping(value = "/attendee/{id}", produces = "application/json")
    EvaluationDto addEvaluationAttendee(@PathVariable("id") Long evaluationId, @RequestBody NewEvaluationAttendeeDto attendee);

    @Operation(
            summary = "Get Evaluations As Trainee",
            description = "Gets a list of all evaluations in which the user is the trainee.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EvaluationDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/trainee", produces = "application/json")
    List<EvaluationDto> getEvaluationsAsTrainee();

    @Operation(
            summary = "Get Evaluations As Attendee",
            description = "Gets a list of all evaluations in which the user is an attendee.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EvaluationDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/attendee", produces = "application/json")
    List<EvaluationDto> getEvaluationsAsAttendee();

    @Operation(
            summary = "Update Evaluation",
            description = "Updates an evaluation and get the updated version.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = EvaluationDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Evaluation not found", content = @Content)
    })
    @PutMapping(value= "/update/{id}", produces = "application/json")
    EvaluationDto updateEvaluation(@RequestBody EvaluationDto evaluation, @PathVariable("id") Long id);

    @Operation(
            summary = "Mark All Seen",
            description = "Marks all evaluations in which the user is trainee or attendee as 'notified'.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @PutMapping("/seen")
    void markAllSeen();

    @Operation(
            summary = "Delete Evaluation Attendee",
            description = "Deletes an attendee from an evaluation.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Evaluation not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}/{attendeeid}")
    void deleteEvaluationAttendee(@PathVariable("id") Long id, @PathVariable("attendeeid") Long attendeeId);

    @Operation(
            summary = "Delete Evaluation",
            description = "Deletes an evaluation.",
            tags = {"Evaluation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Evaluation not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    void deleteEvaluation(@PathVariable("id") Long id);
}
