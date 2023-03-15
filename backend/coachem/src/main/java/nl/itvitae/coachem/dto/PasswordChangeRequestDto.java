package nl.itvitae.coachem.dto;

public record PasswordChangeRequestDto(String oldPassword, String newPassword1, String newPassword2) {
}
