package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.ProgressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/progress")
@Tag(name = "Progress", description = "Progress API")
public interface IProgressAPI {

    @Operation(
            summary = "Get All Progress",
            description = "Gets a list of all progress for a traineeskill.",
            tags = {"Progress"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProgressDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @PostMapping(value = "/new/{traineeSkillId}", produces = "application/json")
    ResponseEntity<ProgressDto> newProgress(@PathVariable("traineeSkillId") Long traineeSkillId, @RequestBody ProgressDto progress);

    @Operation(
            summary = "Get Progress By Id",
            description = "Gets the progress item by id.",
            tags = {"Progress"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProgressDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @GetMapping(value = "/traineeskill/{id}", produces = "application/json")
    List<ProgressDto> getProgressByTraineeSkill(@PathVariable("id") Long id);

    @Operation(
            summary = "Delete Progress",
            description = "Deletes a Progress item.",
            tags = {"Progress"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Progress not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteProgressById(@PathVariable("id") Long id);
}
