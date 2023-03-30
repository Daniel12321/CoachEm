package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.dto.TraineeListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/person")
@Tag(name = "Person", description = "Person API")
public interface IPersonAPI {

    @Operation(
            summary = "Get All Persons",
            description = "Gets a list of all persons.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/all", produces = "application/json")
    List<PersonDto> getAllPersons();

    @Operation(
            summary = "Get Person By Id",
            description = "Gets the person by id.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    PersonDto getPersonById(@PathVariable(value = "id") Long id);

    @Operation(
            summary = "Get Person By Email",
            description = "Gets the person by email.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @GetMapping(value = "/email/{email}", produces = "application/json")
    PersonDto getPersonByEmail(@PathVariable(value = "email") String email);

    @Operation(
            summary = "Get All Trainees",
            description = "Gets a list of all persons who are trainee.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/trainees", produces = "application/json")
    List<PersonDto> getAllTrainees();

    @Operation(
            summary = "Get All Trainees for coach recommendations",
            description = "Gets a list of lists with all trainees per skill if they can be recommended.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TraineeListDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/trainees/forcoach", produces = "application/json")
    List<TraineeListDto> getTraineesForCoachRecommendations();
    @Operation(
            summary = "Accept InfoChange",
            description = "Accepts an infochange, and merges its data with the appropriate person. Also deletes this infochange if successful.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "InfoChange not found", content = @Content)
    })
    @PutMapping(value = "/infochange/{infochangeid}", produces = "application/json")
    ResponseEntity<PersonDto> acceptInfoChange(@PathVariable("infochangeid") Long infoChangeId);

    @Operation(
            summary = "Delete Person",
            description = "Deletes a Person.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deletePersonById(@PathVariable("id") Long id);

    @Operation(
            summary = "Update Person",
            description = "Updates a person, and gets the updated version.",
            tags = {"Person"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    @PutMapping(value = "/update/{id}", produces = "application/json")
    ResponseEntity<PersonDto> updatePersonById(@PathVariable("id") Long id, @RequestBody PersonDto person);
}
