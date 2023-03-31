package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.dto.InviteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/invite")
@Tag(name = "Invite", description = "Invite API")
public interface IInviteAPI {

    @Operation(
            summary = "Get All Invites As Sender",
            description = "Gets a list of all invites sent by the user.",
            tags = {"Invite"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InviteDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping(value = "/sent/{personid}", produces = "application/json")
    List<InviteDto> getSentInvitesByPersonId(@PathVariable("personid") Long personId);

    @Operation(
            summary = "Get All Invites As Receiver",
            description = "Gets a list of all invites received by the user.",
            tags = {"Invite"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InviteDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping(value = "/received/{personid}", produces = "application/json")
    List<InviteDto> getReceivedInvitesByPersonId(@PathVariable("personid") Long personId);

    @Operation(
            summary = "Accept Invite",
            description = "Accept an invite.",
            tags = {"Invite"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Invite not found", content = @Content)
    })
    @GetMapping("/accept/{id}")
    ResponseEntity<Void> acceptInviteRequest(@PathVariable("id") Long id);

    @Operation(
            summary = "Add Invite",
            description = "Adds an invite, and gets the newly created invite.",
            tags = {"Invite"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = InviteDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid invite information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sender not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Receiver not found", content = @Content)
    })
    @PostMapping(value = "/new/{traineeid}/{receiverid}", produces = "application/json")
    ResponseEntity<InviteDto> addInvite(@RequestBody InviteDto invite, @PathVariable("traineeid") Long traineeId, @PathVariable("receiverid") Long receiverId);

    @Operation(
            summary = "Delete Invite",
            description = "Deletes an invite.",
            tags = {"Invite"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Invite not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteInviteById(@PathVariable("id") Long id);
}