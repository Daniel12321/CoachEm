package nl.itvitae.coachem.dto.auth;

public record PasswordChangeRequestDto(String oldPassword, String newPassword1, String newPassword2) {
}
