package nl.itvitae.coachem.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileStorageService {

    protected final Path root = Paths.get("uploads");

    public FileStorageService() {
        this.init();
    }

    public void init() {
        try {
            Files.createDirectories(this.root);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    protected void save(Path path, MultipartFile file) throws IllegalArgumentException {
        if (file.getOriginalFilename() == null)
            throw new IllegalArgumentException("No original file name found");

        Path localFile = path.resolve(file.getOriginalFilename());
        try {
            Files.createDirectories(localFile);
            Files.deleteIfExists(localFile);
            Files.copy(file.getInputStream(), localFile);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    protected Optional<Resource> load(Path file) {
        if (!Files.exists(file))
            return Optional.empty();

        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return Optional.of(resource);
            }
        } catch (MalformedURLException exc) {
            throw new RuntimeException(exc);
        }

        return Optional.empty();
    }

    protected void delete(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
