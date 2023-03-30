package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/category")
@Tag(name = "Category", description = "Category api")
public interface ICategoryAPI {

    @Operation(
            summary = "Get All Categories",
            description = "Gets a list of all categories.",
            tags = {"Category"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @GetMapping(value = "/all", produces = "application/json")
    List<CategoryDto> getAllCategories();

    @Operation(
            summary = "Add Category",
            description = "Adds a new category and gets the newly created category.",
            tags = {"Category"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @PostMapping(value = "/new", produces = "application/json")
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto category);

    @Operation(
            summary = "Delete Category",
            description = "Deletes a category.",
            tags = {"Category"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id);
}
