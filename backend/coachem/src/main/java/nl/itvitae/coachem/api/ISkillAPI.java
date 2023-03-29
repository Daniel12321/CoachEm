package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.SkillDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/skill")
@Tag(name = "Skill", description = "Skill API")
public interface ISkillAPI {

    @Operation(
            summary = "Add Skill",
            description = "Adds a skill, and gets the newly created skill.",
            tags = {"Skill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = SkillDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid skill information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PostMapping(value = "/new/{categoryId}", produces = "application/json")
    ResponseEntity<SkillDto> newSkill(@RequestBody SkillDto skill, @PathVariable("categoryId") Long id);

    @Operation(
            summary = "Get Skill By Id",
            description = "Gets the skill by id.",
            tags = {"Skill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = SkillDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @GetMapping(value = "/get/{id}", produces = "application/json")
    ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id);

    @Operation(
            summary = "Get All Skills",
            description = "Gets a list of all skills.",
            tags = {"Skill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SkillDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/all", produces = "application/json")
    List<SkillDto> getAllSkills();

    @Operation(
            summary = "Update Skill",
            description = "Updates a skill, and gets the updated version.",
            tags = {"Skill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = SkillDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @PutMapping(value = "/update/{id}", produces = "application/json")
    ResponseEntity<SkillDto> updateSkillById(@PathVariable("id") Long id, @RequestBody SkillDto skill);

    @Operation(
            summary = "Delete Skill",
            description = "Deletes a Skill.",
            tags = {"Skill"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Skill not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteSkillById(@PathVariable("id") Long id);
}
