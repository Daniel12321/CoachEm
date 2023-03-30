package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.InfoChangeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/infochange")
@Tag(name = "InfoChange", description = "InfoChange api")
public interface IInfoChangeAPI {

    @Operation(
            summary = "Get InfoChange",
            description = "Gets the infochange by id.",
            tags = {"InfoChange"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = InfoChangeDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "InfoChange not found", content = @Content),
    })
    @GetMapping(value = "/get/{id}", produces = "application/json")
    ResponseEntity<InfoChangeDto> getInfoChangeById(@PathVariable("id") Long id);

    @Operation(
            summary = "Get All InfoChanges",
            description = "Gets a list of all infochanges.",
            tags = {"InfoChange"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InfoChangeDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/all", produces = "application/json")
    List<InfoChangeDto> getAllInfoChanges();

    @Operation(
            summary = "Add InfoChange",
            description = "Adds a new infochange and gets the newly created infochange.",
            tags = {"InfoChange"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = InfoChangeDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @PostMapping(value = "/new", produces = "application/json")
    InfoChangeDto addInfoChangeRequest(@RequestBody InfoChangeDto infoChange);

    @Operation(
            summary = "Delete InfoChange",
            description = "Deletes an infochange.",
            tags = {"InfoChange"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "InfoChange not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteInfoChangeById(@PathVariable("id") Long id);
}
