package nl.itvitae.coachem.dto.auth;

import nl.itvitae.coachem.dto.PersonDto;

public record LoginResponseDto(String token, PersonDto person) {
}
