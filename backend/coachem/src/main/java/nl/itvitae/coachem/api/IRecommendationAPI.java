package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.FeedbackDto;
import nl.itvitae.coachem.dto.InfoChangeDto;
import nl.itvitae.coachem.dto.RecommendationDto;
import nl.itvitae.coachem.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/recommendation")
@Tag(name = "Recommendation", description = "Recommendation API")
public interface IRecommendationAPI {
    @Operation(
            summary = "Get Recommendation",
            description = "Gets the recommendations by person id.",
            tags = {"Recommendation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = RecommendationDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recommendation not found", content = @Content)
    })
    @GetMapping("/trainee/{id}")
    List<RecommendationDto> getRecommendationsByTraineeId(@PathVariable("id") Long id);

    @Operation(
            summary = "Add recommendation",
            description = "Adds a new recommendation for a certain person and skill.",
            tags = {"Recommendation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = RecommendationDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @PostMapping("/new/{personid}/{skillid}")
    RecommendationDto newRecommendation(@PathVariable("personid") Long personId,
                                               @PathVariable("skillid") Long skillId);

    @Operation(
            summary = "Delete Recommendation",
            description = "Deletes a recommendation.",
            tags = {"Recommendation"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recommendation not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteRecommendationById(@PathVariable("id") Long id);
}
