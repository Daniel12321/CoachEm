package nl.itvitae.coachem.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadDto(MultipartFile file) {
}
