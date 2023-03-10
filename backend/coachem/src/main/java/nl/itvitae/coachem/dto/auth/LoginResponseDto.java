package nl.itvitae.coachem.dto.auth;

import lombok.NoArgsConstructor;

public record LoginResponseDto(String email, String token) {
}
