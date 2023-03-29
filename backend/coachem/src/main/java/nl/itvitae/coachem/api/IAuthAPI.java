package nl.itvitae.coachem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.dto.auth.LoginRequestDto;
import nl.itvitae.coachem.dto.auth.LoginResponseDto;
import nl.itvitae.coachem.dto.auth.PasswordChangeRequestDto;
import nl.itvitae.coachem.dto.auth.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth api")
public interface IAuthAPI {

    @Operation(
            summary = "Login",
            description = "Sends a token and personal info, when valid email and password are submitted.",
            tags = {"Auth"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email & password combination", content = @Content)
    })
    @PostMapping(value = "/login", produces = "application/json")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request);

    @Operation(
            summary = "Register",
            description = "Creates a new account with the provided information.",
            tags = {"Auth"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid personal information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @PostMapping(value = "/register", produces = "application/json")
    ResponseEntity<PersonDto> register(@RequestBody RegisterRequestDto request);

    @Operation(
            summary = "Change password",
            description = "Changes the password for a user.",
            tags = {"Auth"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid personal information", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication", content = @Content)
    })
    @PostMapping("/change_password")
    ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequestDto request);
}
