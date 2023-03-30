package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.TraineeSkillDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/traineeskill")
@Tag(name = "TraineeSkill", description = "TraineeSkill API")
public interface ITraineeSkillAPI {

    @Operation(
            summary = "Add TraineeSkill",
            description = "Adds an traineeskill, and gets the newly created traineeskill.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = TraineeSkillDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid traineeskill information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @PostMapping(value = "/new/{traineeid}/{skillid}", produces = "application/json")
    ResponseEntity<TraineeSkillDto> newTraineeSkill(@RequestBody TraineeSkillDto traineeSkill,
                                           @PathVariable("traineeid") Long traineeId,
                                           @PathVariable("skillid") Long skillId);

    @Operation(
            summary = "Upload File",
            description = "Uploads a certificate or report for a traineeskill.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @PostMapping("/upload/{id}")
    void upload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file);

    @Operation(
            summary = "Download File",
            description = "Deletes a certificate or report for a traineeskill.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @GetMapping("/download/{id}")
    @ResponseBody
    ResponseEntity<Resource> serveFile(@PathVariable("id") Long id);

    @Operation(
            summary = "Get TraineeSkill By Id",
            description = "Gets the traineeskill by id.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = TraineeSkillDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @GetMapping(value = "/get/{id}", produces = "application/json")
    ResponseEntity<TraineeSkillDto> getTraineeSkillById(@PathVariable("id") Long id);

    @Operation(
            summary = "Get TraineeSkills By UserId",
            description = "Gets a listo f all traineeskills by user id.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TraineeSkillDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @GetMapping(value = "/user/{userid}", produces = "application/json")
    List<TraineeSkillDto> getTraineeSkillByUser(@PathVariable("userid") Long userId);

    @Operation(
            summary = "Update TraineeSkill",
            description = "Updates a traineeskill, and gets the updated version.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = TraineeSkillDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @PutMapping(value = "/update/{id}", produces = "application/json")
    ResponseEntity<TraineeSkillDto> updateTraineeSkillById(@PathVariable("id") Long id, @RequestBody TraineeSkillDto traineeSkill);

    @Operation(
            summary = "Delete TraineeSkill",
            description = "Deletes a traineeskill.",
            tags = {"TraineeSkill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "TraineeSkill not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteTraineeSkillById(@PathVariable("id") Long id);
}
