package nl.itvitae.coachem.dto.auth;

public record LoginResponseDto(String email, String role, String token) {
}
